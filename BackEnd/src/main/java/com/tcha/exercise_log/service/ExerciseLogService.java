package com.tcha.exercise_log.service;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import com.tcha.exercise_log.mapper.ExerciseLogMapper;
import com.tcha.exercise_log.repository.ExerciseLogRepository;
import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.pt_class.entity.PtClass;
import com.tcha.pt_class.repository.PtClassRepository;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.pt_live.repository.PtLiveRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import com.tcha.utils.pagination.MultiResponseDto;
import com.tcha.utils.upload.service.S3Uploader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseLogService {

    private final ExerciseLogRepository exerciseLogRepository;
    private final TrainerRepository trainerRepository;
    private final PtLiveRepository ptLiveRepository;
    private final ExerciseLogMapper exerciseLogMapper;
    private final S3Uploader s3Uploader;
    private final PtClassRepository ptClassRepository;


    //운동일지 저장
    @Transactional
    public ExerciseLog createExerciseLog(long ptLiveId) {

        //ptlive 찾기 (유효성 검증 완)
        PtLive ptLive = findVerifiedByPtLiveId(ptLiveId);

        ExerciseLog exerciseLog = new ExerciseLog().builder()
                .title("")
                .contents(new ArrayList<>())
                .images(new ArrayList<>())
                .videos(new ArrayList<>())
                .status(ExerciseLog.exerciseLogStatus.WRITE)
                .ptLive(ptLive)

                .build();
//        //ptlive set
//        exerciseLog.setPtLive(ptLive);

        //트레이너 id 찾기 => 운동일지 생성 시에는 무조건 트레이너 존재해야 함
        String trainerId = ptLive.getTrainerId();

        //트레이너 찾기 (유효성 검증 완)
        Trainer trainer = findVerifiedTrainerById(trainerId);

        //DB에 새로운 운동일지 생성
        exerciseLogRepository.save(exerciseLog);

        return exerciseLog;
    }


    //Pagenation으로 운동 일지를 불러오기 (일단 에러 핸들링 X)
    @Transactional(readOnly = true)
    public MultiResponseDto findExerciseLogPages(int page, int size) {
        Page<ExerciseLog> exerciseLogPage = exerciseLogRepository.findAll(PageRequest.of(page - 1, size, Sort.by("id").descending()));
        List<ExerciseLog> exercises = exerciseLogPage.getContent();
        List<ExerciseLogDto.Response> responses = new ArrayList<>();

        for (ExerciseLog e : exercises) {
            responses.add(exerciseLogMapper.exerciseLogToResponse(e, findTrainerNameByExerciseLog(e)));
        }
        return new MultiResponseDto<>(responses, exerciseLogPage);

    }

    //운동일지 1개 찾기(PK)
    @Transactional(readOnly = true)
    public ExerciseLogDto.Response findExerciseLog(Long id) {
        ExerciseLog exerciseLog = findVerifiedById(id);

        return exerciseLogMapper.exerciseLogToResponse(exerciseLog,
                findTrainerNameByExerciseLog(exerciseLog));
    }

    //운동일지 1개 찾기(ptLiveId)
    @Transactional(readOnly = true)
    public ExerciseLogDto.Response findExerciseLogByLiveId(Long liveId) {
        //운동일지에 대한 유효성 검증 완
        ExerciseLog exerciseLog = findVerifiedById(liveId);

        return exerciseLogMapper.exerciseLogToResponse(exerciseLog,
                findTrainerNameByExerciseLog(exerciseLog));
    }


    /**
     * 업데이트: 트레이너가 진행 => ptlive를 통해서 운동일지를 업로드 할수 있음 => 따라서 트레이너에 대한 유효성 검사는 필요없음
     */
    @Transactional
    public ExerciseLogDto.Response updateExerciseLog(ExerciseLog existExerciseLog,
                                                     ExerciseLogDto.Patch patchRequest) {

//        existExerciseLog.setContent(patchRequest.getContent());
        existExerciseLog.setTitle(patchRequest.getTitle());

        // 업데이트 전에 기존 데이터 먼저 삭제해야됨 -> s3의 기존 정보(이미지, 비디오) delete
        List<String> imgList = existExerciseLog.getImages();
//        List<String> videoList = existExerciseLog.getVideos();

        if (!imgList.isEmpty()) {
            for (String s : imgList) {
                s3Uploader.delete(s);
            }
//            for (String s : videoList) {
//                s3Uploader.delete(s);
//            }
        }

        List<String> content = new ArrayList<>();
        List<String> image = new ArrayList<>();

        for (ExerciseLogDto.Content c : patchRequest.getContents()) {
            //새로운 이미지, 비디오 저장
            content.add(c.getText());
            image.add(c.getImage());
        }
        existExerciseLog.setContents(content);
        existExerciseLog.setImages(image);

        return exerciseLogMapper.exerciseLogToResponse(exerciseLogRepository.save(existExerciseLog),
                findTrainerNameByExerciseLog(existExerciseLog));
    }

    //운동일지 삭제
    @Transactional
    public void deleteExerciseLog(Long id) {

        ExerciseLog findExerciseLog = findVerifiedById(id);

        // 업데이트 전에 기존 데이터 먼저 삭제해야됨 -> s3의 기존 정보(이미지, 비디오) delete
        List<String> imgList = findExerciseLog.getImages();
        List<String> videoList = findExerciseLog.getVideos();

        for (String s : imgList) {
            s3Uploader.delete(s);
        }
        for (String s : videoList) {
            s3Uploader.delete(s);
        }

        exerciseLogRepository.delete(findExerciseLog);
    }

    //작성 및 작성완료는 해당 일지를 확인할 수 있는 트레이너만 접근이 가능하도록 다른 도메인에서 정해져있을 것임
    //그리고 작성완료는 트레이너가 하므로, 트레이너 이름 정보 필요 없음
    @Transactional
    public ExerciseLogDto.Response patchWriteDoneExerciseLog(ExerciseLog exerciseLog, ExerciseLogDto.Patch patchRequest) {

        exerciseLog.setTitle(patchRequest.getTitle());

        // 업데이트 전에 기존 데이터 먼저 삭제해야됨 -> s3의 기존 정보(이미지, 비디오) delete
        List<String> imgList = exerciseLog.getImages();
//        List<String> videoList = exerciseLog.getVideos();

        if (!imgList.isEmpty()) {
            for (String s : imgList) {
                s3Uploader.delete(s);
            }
//            for (String s : videoList) {
//                s3Uploader.delete(s);
//            }
        }

        List<String> content = new ArrayList<>();
        List<String> image = new ArrayList<>();

        for (ExerciseLogDto.Content c : patchRequest.getContents()) {
            //새로운 이미지, 비디오 저장
            content.add(c.getText());
            image.add(c.getImage());
        }
        exerciseLog.setContents(content);
        exerciseLog.setImages(image);

        //운동일지 상태 읽기로 변경
        exerciseLog.setStatus(ExerciseLog.exerciseLogStatus.READ);

        return exerciseLogMapper.exerciseLogToResponse(exerciseLogRepository.save(exerciseLog),
                findTrainerNameByExerciseLog(exerciseLog));
    }

    /**
     * 운동 시작 후, 클래스 아이디로 운동 시작 시간을 찾아서 => 배치 돌리기 배치 돌리는 시간 기준: 30분마다 한번씩 진행 시작시간과 비교해가지고 -> 시작시간 기준
     * 1시간 지났으면 바로 read로 변경
     *
     * @Scheduled(fixedRate = 30000) //30 * 60 * 1000ms근데 이것만 작성하면 run 버튼을 누른 뒤로 30분마다 실행되는 것이라 시간을
     * 정확히 통제할 수 없음
     */

    @Transactional
    @Scheduled(cron = "0 30 * * * *") // 30분마다 실행
    public void executeExerciseLogStatusChange() {
        //메소드 실행시각
        LocalDateTime nowTime = LocalDateTime.now();

        // 운동일지 불러오기 (운동 시작 시간이 없으므로, r/w여부로 조회)
        List<ExerciseLog> list = exerciseLogRepository.findByStatus().get();

        //불러온 운동일지 상태 for문 돌면서 수정하기
        for (ExerciseLog e : list) {
            //해당 운동 클래스 가져오기
            PtClass ptClass = ptClassRepository.findById(e.getPtLive().getPtClassId()).get();

            //해당 운동일지의 수업 시작 시간 가져오기
            LocalTime startTime = ptClass.getStartTime();
            LocalDate startDate = ptClass.getStartDate();

            LocalDateTime start = LocalDateTime.of(startDate, startTime);

            //운동 시간 확인, (운동 시작시각 + 1) + 24 보다 이전이라면, R/W변경
            if (start.isBefore(nowTime.minusHours(25))) {
                e.setStatus(ExerciseLog.exerciseLogStatus.READ);
            }
        }

    }

    //존재하는 트레이너인지 대한 유효성 검증
    @Transactional
    public Trainer findVerifiedTrainerById(String trainerId) {

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TRAINER_NOT_FOUND));

        return trainer;
    }

    //새로운 운동일지 생성 시, 중복 체크 검증
    //-> 운동일지 db에서 PTliveid가 중복된다면, 이미 운동일지는 생성된 상태 => EXERCISELOG_EXISTS반환
    @Transactional
    public void duplicateVerifiedByPtLiveId(Long ptLiveId) throws BusinessLogicException {
        exerciseLogRepository.findByLiveId(ptLiveId).ifPresent(exerciseLog -> {
            throw new BusinessLogicException(ExceptionCode.EXERCISELOG_EXISTS);
        });

    }

    //존재하는 ptlive인지에 대한 검증
    @Transactional
    public PtLive findVerifiedByPtLiveId(Long ptLiveId) throws BusinessLogicException {
        PtLive ptLive = ptLiveRepository.findById(ptLiveId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PT_LIVE_NOT_FOUND));
        return ptLive;
    }


    //운동일지 삭제 & 조회 & 작성 완료시 존재하는 운동일지인지에 대해 운동일지 아이디로 검증
    @Transactional
    public ExerciseLog findVerifiedById(Long id) {
        ExerciseLog exerciseLog = exerciseLogRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.EXERCISELOG_NOT_FOUND));
        return exerciseLog;
    }

    //트레이너 이름 찾기
    @Transactional
    public String findTrainerNameByExerciseLog(ExerciseLog exerciseLog) {
        return trainerRepository.findById(exerciseLog.getPtLive().getTrainerId()).get()
                .getUserProfile().getName();
    }


}
