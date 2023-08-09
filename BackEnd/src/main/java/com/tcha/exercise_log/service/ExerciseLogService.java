package com.tcha.exercise_log.service;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import com.tcha.exercise_log.mapper.ExerciseLogMapper;
import com.tcha.exercise_log.repository.ExerciseLogRepository;
import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.pt_live.repository.PtLiveRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import com.tcha.utils.upload.service.S3Uploader;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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


    //운동일지 저장
    @Transactional
    public ExerciseLogDto.Response createExerciseLog(ExerciseLog exerciseLog, long ptLiveId) {

        //ptlive 찾기 (유효성 검증 완)
        PtLive ptLive = findVerifiedByPtLiveId(ptLiveId);

        //트레이너 id 찾기
        String trainerId = ptLive.getTrainerId();

        //트레이너 찾기 (유효성 검증 완)
        Trainer trainer = findVerifiedTrainerById(trainerId);

        exerciseLog.setPtLive(ptLive);
        return exerciseLogMapper.exerciseLogToResponse(exerciseLog, trainer.getUserProfile().getName());
    }


    //Pagenation으로 운동 일지를 불러오기 (일단 에러 핸들링 X)
    @Transactional(readOnly = true)
    public Page<ExerciseLog> findExerciseLogPages(int page, int size) {

        return exerciseLogRepository.findAll(

                PageRequest.of(page - 1, size, Sort.by("id").descending()));
    }

    //운동일지 1개 찾기(PK)
    @Transactional(readOnly = true)
    public ExerciseLogDto.Response findExerciseLog(Long id) {
        ExerciseLog exerciseLog = findVerifiedById(id);

        return exerciseLogMapper.exerciseLogToResponse(exerciseLog, findTrainerNameByExerciseLog(exerciseLog));
    }

    //운동일지 1개 찾기(ptLiveId)
    @Transactional(readOnly = true)
    public ExerciseLogDto.Response findExerciseLogByLiveId(Long liveId) {
        //운동일지에 대한 유효성 검증 완
        ExerciseLog exerciseLog = findVerifiedById(liveId);

        return exerciseLogMapper.exerciseLogToResponse(exerciseLog, findTrainerNameByExerciseLog(exerciseLog));
    }


    @Transactional
    public ExerciseLogDto.Response updateExerciseLog(ExerciseLog existExerciseLog, ExerciseLogDto.Patch patchRequest, Long id) {

        existExerciseLog.setTitle(patchRequest.getTitle());
        existExerciseLog.setContent(patchRequest.getContent());

        // 업데이트 전에 기존 데이터 먼저 삭제해야됨 -> s3의 기존 정보(이미지, 비디오) delete
        List<String> imgList = existExerciseLog.getImages();
        List<String> videoList = existExerciseLog.getVideos();

        for (String s : imgList) {
            s3Uploader.delete(s);
        }
        for (String s : videoList) {
            s3Uploader.delete(s);
        }

        //새로운 이미지, 비디오 저장
        existExerciseLog.setImages(patchRequest.getImages());
        existExerciseLog.setVideos(patchRequest.getVideos());

        return exerciseLogMapper.exerciseLogToResponse(exerciseLogRepository.save(existExerciseLog), findTrainerNameByExerciseLog(existExerciseLog));
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

    //존재하는 트레이너인지 대한 유효성 검증
    public Trainer findVerifiedTrainerById(String trainerId) {

        Trainer trainer = trainerRepository.findById(UUID.fromString(trainerId)).orElseThrow(() -> new BusinessLogicException(ExceptionCode.TRAINER_NOT_FOUND));

        return trainer;
    }

    //새로운 운동일지 생성 시, 중복 체크 검증
    //-> 운동일지 db에서 PTliveid가 중복된다면, 이미 운동일지는 생성된 상태 => EXERCISELOG_EXISTS반환
    public void duplicateVerifiedByPtLiveId(Long ptLiveId) throws BusinessLogicException {
        exerciseLogRepository.findByLiveId(ptLiveId).ifPresent(exerciseLog -> {
            throw new BusinessLogicException(ExceptionCode.EXERCISELOG_EXISTS);
        });

    }

    //존재하는 ptlive인지에 대한 검증
    public PtLive findVerifiedByPtLiveId(Long ptLiveId) throws BusinessLogicException {
        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.PTLIVE_NOT_FOUND));
        return ptLive;
    }


    //운동일지 삭제 & 조회시 존재하는 운동일지인지에 대해 운동일지 아이디로 검증
    public ExerciseLog findVerifiedById(Long id) {
        ExerciseLog exerciseLog = exerciseLogRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.EXERCISELOG_NOT_FOUND));
        return exerciseLog;
    }

    public String findTrainerNameByExerciseLog(ExerciseLog exerciseLog) {
        String result = "알수없음";
        try {
            return trainerRepository.findById(UUID.fromString(exerciseLog.getPtLive().getTrainerId())).get().getUserProfile().getName();
        } catch (Exception e) {
            return result;
        }

    }


}
