package com.tcha.pt_class.service;

import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.exercise_log.entity.ExerciseLog.exerciseLogStatus;
import com.tcha.exercise_log.service.ExerciseLogService;
import com.tcha.pt_class.dto.PtClassDto;
import com.tcha.pt_class.entity.PtClass;
import com.tcha.pt_class.mapper.PtClassMapper;
import com.tcha.pt_class.repository.PtClassRepository;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.pt_live.repository.PtLiveRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user.entity.User;
import com.tcha.user.repository.UserRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import com.tcha.user_profile.service.UserProfileService;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class PtClassService {

    private final PtClassRepository ptClassRepository;
    private final TrainerRepository trainerRepository;
    private final PtLiveRepository ptLiveRepository;
    private final UserRepository userRepository;
    private final PtClassMapper ptClassMapper;
    private final UserProfileService userProfileService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ExerciseLogService exerciseLogService;
    private final UserProfileRepository userProfileRepository;

    public List<PtClassDto.Response> createPtClass(PtClassDto.Post postRequest) {

        // 요청을 보낸 트레이너가 유효한 트레이너인지 확인 (에러 핸들링 추가)
        Trainer trainer = trainerRepository.findById(
                postRequest.getTrainerId()).orElseThrow();

        //

        // pt 수업 등록할 날짜
        LocalDate date = postRequest.getDate();

        // 반환값으로 보낼 데이터 (생성한 수업 리스트)
        List<PtClass> classList = new ArrayList<>();

        // 입력된 시간들에 대해서 ptClass 생성 (시간에 대한 유효성 검증 추가 -> 프론트에서 한대요)
        for (LocalTime time : postRequest.getStartTimeList()) {
            classList.add(ptClassRepository.save(
                    ptClassMapper.classPostDtoToClass(trainer, date, time)));
        }

        List<PtClassDto.Response> response = new ArrayList<>();
        for (PtClass pt : classList) {
            response.add(ptClassMapper.classToClassResponseDto(pt, null, 0, null, null));
        }

        return response;
    }

    //트레이너의 PTclass 찾기
    public List<PtClassDto.Response> findPtClassByTrainer(String trainerId) {

        // 요청을 보낸 트레이너가 유효한 트레이너인지 확인
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.TRAINER_NOT_FOUND));

        List<PtClass> trainerClassList = ptClassRepository.findClassByTrainer(trainer.getId());
        List<PtClassDto.Response> response = new ArrayList<>();

        //status 넣어주기
        for (PtClass pt : trainerClassList) {
            //예약이 되어 있는 상태
            if (pt.getPtLiveId() != null) {
                Optional<PtLive> ptLive = ptLiveRepository.findById(pt.getPtLiveId());
                PtLive.PtliveStatus status = ptLive.get().getStatus();
                UserProfile userProfile = ptLive.get().getUserProfile();
                ExerciseLog.exerciseLogStatus exerciseLogStatus = ptLive.get().getExerciseLogs()
                        .getStatus();
                if (ptLive.get().getReview() != null) {
                    //리뷰 아이디 존재 -> 값 넣어주기
                    Long reviewId = ptLive.get().getReview().getId();
                    response.add(ptClassMapper.classToClassResponseDto(pt, status, reviewId, userProfile, exerciseLogStatus));

                }
                //리뷰아이디 존재하지 않는 상태
                else {
                    response.add(ptClassMapper.classToClassResponseDto(pt, status, 0, userProfile, exerciseLogStatus));
                }
            }
            //예약이 존재하지 않는 상태
            else {
                response.add(ptClassMapper.classToClassResponseDto(pt, null, 0, null, null));
            }
        }
        return response;
    }

    //유저의 PTclass 찾기
    public List<PtClassDto.Response> findPtClassByUser(long userProfileId) {

        // 유저 유효성 검사 (추가하기)
        UserProfile userProfile = userProfileService.findVerifiedUserProfile(userProfileId);

        // 유저가 진행한 라이브 데이터 가져오기
        List<PtLive> userLiveList = ptLiveRepository.findAllByUserProfile(userProfile);
        List<PtClass> userClassList = new ArrayList<>();

        // 라이브 객체를 통해 클래스 객체 가져와서 리스트에 담기
        for (PtLive ptLive : userLiveList) {
            userClassList.add(ptClassRepository.findById(ptLive.getPtClassId()).orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.PT_CLASS_NOT_FOUND)));
        }

        Collections.sort(userClassList, new Comparator<PtClass>() {
            @Override
            public int compare(PtClass ptClass1, PtClass ptClass2) {
                int dateComparison = ptClass1.getStartDate().compareTo(ptClass2.getStartDate());

                if (dateComparison != 0) {
                    return dateComparison; // startDate가 다르면 그 기준으로 정렬
                } else {
                    return ptClass1.getStartTime()
                            .compareTo(ptClass2.getStartTime()); // startTime으로 정렬
                }
            }
        });

        List<PtClassDto.Response> response = new ArrayList<>();
        for (PtClass pt : userClassList) {
            Optional<PtLive> ptLive = ptLiveRepository.findById(pt.getPtLiveId());
            PtLive.PtliveStatus status = ptLive.get().getStatus();

            //예약된 수업이 존재하는 경우
            if (ptLive.isPresent()) {
                ExerciseLog.exerciseLogStatus exerciseLogStatus = ptLive.get().getExerciseLogs()
                        .getStatus();
                //리뷰 작성 후
                if (ptLive.get().getReview() != null) {
                    //리뷰 아이디 존재 -> 값 넣어주기
                    Long reviewId = ptLive.get().getReview().getId();

                    response.add(ptClassMapper.classToClassResponseDto(pt, status, reviewId, userProfile, exerciseLogStatus));
                }
                //리뷰 작성전
                else {
                    response.add(ptClassMapper.classToClassResponseDto(pt, status, 0, userProfile, exerciseLogStatus));
                }
            }
            //예약한 수업이 존재하지 않는 경우
            else {
                response.add(ptClassMapper.classToClassResponseDto(pt, null, 0, null, null));
            }
        }
        /**위의 코드는 에러날 수도 아니면 코드, 아래 코드로 바꿔야 될수도 내일 생각해보기*/
//        if (pt.getPtLiveId() != null) {
//            Optional<PtLive> ptLive = ptLiveRepository.findById(pt.getPtLiveId());
//            response.add(ptClassMapper.classToClassResponseDto(pt, ptLive.get().getStatus()));
//        } else {
//            response.add(ptClassMapper.classToClassResponseDto(pt, null));
//        }

        return response;
    }

    public PtClassDto.Response updatePtClass(PtClassDto.Patch patchRequest) {

        // user 객체 가져오기 (유효성 검증 로직 추가 :: 활성상태 유저인지 확인, 일반 유저인지 확인)
        User user = userRepository.findById(patchRequest.getUserId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        // key : value를 가지는 redis 자료구조 불러오기(Pt 누적 횟수 업데이트를 위해)
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        // pt 누적 횟수별 정렬을 위해
        ZSetOperations<String, String> ZSetOperations = redisTemplate.opsForZSet();

        // ptClass 객체 가져오기 (유효성 검증 로직 추가)

        PtClass ptClass = ptClassRepository.findVerifiedClassById(patchRequest.getPtClassId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PT_CLASS_NOT_FOUND));

        // 예약 신청인지 취소인지 판단
        Long ptLiveId = ptClass.getPtLiveId();
        // 1. 수업 예약 신청이라면 (저장된 라이브 아이디 없음 = null)
        if (ptLiveId == null) {
            // 결제 로직
            String trainerId = ptClass.getTrainer().getId();
            String ptCountKey = "ptCount:" + trainerId;

            String s = valueOperations.get(ptCountKey);

            valueOperations.set(ptCountKey, String.valueOf(Double.parseDouble(s) + 1.0));
            ZSetOperations.add("PT", trainerId, Double.parseDouble(s) + 1.0);




            // 결제 성공 시, 새로운 ptLive 객체 생성해서 DB insert
            PtLive createdPtLive = ptLiveRepository.save(
                    PtLive.builder()
                            .ptClassId(ptClass.getId())
                            .userProfile(user.getUserProfile())
                            .trainerId(ptClass.getTrainer().getId())
                            .status(PtLive.PtliveStatus.INACCESSIBLE)
                            .exerciseLogs(null)
                            .build());

            // ptClass에 ptLive 아이디 추가해주기 (update)
            ptClass.setPtLiveId(createdPtLive.getId());
            ExerciseLog exerciseLog = exerciseLogService.createExerciseLog(ptClass.getPtLiveId());

            createdPtLive.setExerciseLogs(exerciseLog);
            /** 여기에 운동일지 생성하는 코드 넣어주세요*/

            return ptClassMapper.classToClassResponseDto(ptClass, PtLive.PtliveStatus.INACCESSIBLE, 0, user.getUserProfile(), exerciseLogStatus.WRITE);
        }

        // 2. 수업 예약 취소라면,
        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.PT_LIVE_NOT_FOUND));

        // 취소 요청을 보낸 유저가 예약한 유저와 일치하는지 확인
        if (user.getUserProfile().equals(ptLive.getUserProfile())) {
            /** 운동일지 삭제하기 */
            exerciseLogService.deleteExerciseLog(ptClass.getPtLiveId());

            ptClass.setPtLiveId(null);
            ptLive.setStatus(null);
            ptLive.setUserProfile(null);

            String trainerId = ptLive.getTrainerId();
            String ptCountKey = "ptCount:" + trainerId;

            String s = valueOperations.get(ptCountKey);
            valueOperations.set(ptCountKey, String.valueOf(Double.parseDouble(s) - 1.0));
            ZSetOperations.add("PT", trainerId, Double.parseDouble(s) - 1.0);
            return ptClassMapper.classToClassResponseDto(ptClass, null, 0, null, null);
        }
        throw new BusinessLogicException(ExceptionCode.PT_CLASS_RESERVATION_EXIST);
    }

    public List<PtClassDto.Response> findPtClassByDatetime(PtClassDto.Get getRequest) {

        LocalDate date = getRequest.getDate();
        LocalTime fromTime = getRequest.getFromTime();
        LocalTime toTime = getRequest.getToTime();

        // 1. 날짜만 선택한 경우 -> 해당 날짜의 수업만 검색 -> where로 날짜 필터링
        // 2. 시간만 선택한 경우 -> 오늘 이후 날짜의 해당 시간(구간) 수업만 검색
        // 3. 둘 다 선택한 경우 -> 해당 날짜의 해당 시간(구간)의 수업만 검색

        if (date == null) { // 시간만 선택한 경우
            date = LocalDate.now();
        }
        List<PtClass> datetimeClassList = ptClassRepository.findClassByTime(date, fromTime, toTime);

        return ptClassMapper.classListToClassResponseDtoList(datetimeClassList);
    }


    public PtClassDto.Response deletePtClass(long ptClassId, String trainerId) {

        // 삭제하고자 하는 수업에 대한 유효성 검증
        PtClass ptClass = ptClassRepository.findVerifiedClassById(ptClassId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.PT_CLASS_NOT_FOUND));

        // 1. 요청 트레이너의 수업인지 확인 (다른 트레이너의 수업이면 삭제 불가)
        if (!ptClass.getTrainer().getId().toString().equals(trainerId)) {
            throw new BusinessLogicException(ExceptionCode.PT_CLASS_TRAINER_MISMATCH);
        }
        // 2. 이미 예약된 수업인지 확인 (예약된 상태라면 삭제 불가)
        if (ptClass.getPtLiveId() != null) {
            throw new BusinessLogicException(ExceptionCode.PT_CLASS_RESERVATION_EXIST);
        }

        // 수업 삭제(삭제 상태값 컬럼 변경)
        ptClass.setIsDel(1);

        return ptClassMapper.classToClassResponseDto(ptClass, null, 0, null, null);
    }

    /************** 상태 변경 로직 작성 ***************/
    /**************** 30분마다 돌면서 "진행" -> "종료 가능" 상태로 변경 ****************/
    @Transactional
    @Scheduled(cron = "0 0 * * * *") //정각에 실행
    public void executePtLiveStatusHourTerminable() {
        executePtLiveStatusChangeTerminable();
    }

    @Transactional
    @Scheduled(cron = "0 30 * * * *")// 30분에 실행
    public void executePtLiveStatusHalfHourTerminable() {
        executePtLiveStatusChangeTerminable();
    }


    //진행 -> 종료가능 변경
    @Transactional
    public void executePtLiveStatusChangeTerminable() {
        //메소드 실행시각
        LocalDateTime nowTime = LocalDateTime.now();

        // status로 pt라이브 불러오기 (Progress 가져오기)
        List<PtLive> list = ptLiveRepository.findAllByStatusAccessible().get();

        //불러온 운동일지 상태 for문 돌면서 수정하기
        for (PtLive pt : list) {
            //해당 운동 클래스 가져오기
            PtClass ptClass = ptClassRepository.findById(pt.getPtClassId()).get();

            //해당 수업 시작 시간 가져오기
            LocalTime startTime = ptClass.getStartTime();
            LocalDate startDate = ptClass.getStartDate();

            LocalDateTime start = LocalDateTime.of(startDate, startTime);

            //운동 시간 확인, 운동 시작시각 + 1이면 종료가능 상태로 변경
            if (start.isBefore(nowTime.minusHours(1))) {
                pt.setStatus(PtLive.PtliveStatus.TERMINABLE);
            }

        }

    }

    @Transactional
    @Scheduled(cron = "0 10 * * * *") // 매번 n:10분에 실행
    public void executePtLiveStatusHourTermination() {
        executePtLiveStatusChangeTermination();
    }

    @Transactional
    @Scheduled(cron = "0 40 * * * *") // 매번 n:40분에 실행
    public void executePtLiveStatusHalfHourTermination() {
        executePtLiveStatusChangeTermination();
    }


    //종료가능 -> 종료
    @Transactional
    public void executePtLiveStatusChangeTermination() {
        //메소드 실행시각
        LocalDateTime nowTime = LocalDateTime.now();

        // status로 pt라이브 불러오기 (Progress 가져오기)
        List<PtLive> list = ptLiveRepository.findAllByStatusTerminable().get();

        //불러온 운동일지 상태 for문 돌면서 수정하기
        for (PtLive pt : list) {
            //해당 운동 클래스 가져오기
            PtClass ptClass = ptClassRepository.findById(pt.getPtClassId()).get();

            //해당 수업 시작 시간 가져오기
            LocalTime startTime = ptClass.getStartTime();
            LocalDate startDate = ptClass.getStartDate();

            LocalDateTime start = LocalDateTime.of(startDate, startTime);

            //운동 시간 확인, 운동 시작시각 + 1이면 종료가능 상태로 변경
            if (start.isBefore(nowTime.minusMinutes(70))) {
                pt.setStatus(PtLive.PtliveStatus.TERMINATION);
            }

        }
    }

    @Transactional
    @Scheduled(cron = "0 55 * * * *") //55분에 실행
    public void executePtLiveStatus55() {
        executePtLiveStatusChangeAccessible();
    }

    @Transactional
    @Scheduled(cron = "0 25 * * * *")// 25분에 실행
    public void executePtLiveStatus25() {
        executePtLiveStatusChangeAccessible();
    }


    //접근불가 -> 진행 변경
    @Transactional
    public void executePtLiveStatusChangeAccessible() {
//        System.out.println("시각: " + LocalDateTime.now());
        //메소드 실행시각
        LocalDateTime nowTime = LocalDateTime.now();

//        LocalDateTime start = LocalDateTime.now().plusMinutes(5);
//        System.out.println("PT시작: " + start);
//
//        System.out.println(">> " + start.isBefore(nowTime.minusMinutes(5)));
//        System.out.println("?? " + start.isAfter(nowTime.minusMinutes(5)));

        // status로 pt라이브 불러오기 (Progress 가져오기)
        List<PtLive> list = ptLiveRepository.findAllByStatusInaccessible().get();

        //불러온 운동일지 상태 for문 돌면서 수정하기
        for (PtLive pt : list) {
            //해당 운동 클래스 가져오기
            PtClass ptClass = ptClassRepository.findById(pt.getPtClassId()).get();

            //해당 수업 시작 시간 가져오기
            LocalTime startTime = ptClass.getStartTime();
            LocalDate startDate = ptClass.getStartDate();

            LocalDateTime start = LocalDateTime.of(startDate, startTime);

//            운동 시간 확인, 운동 시작시각 + 1이면 종료가능 상태로 변경
            if (start.isAfter(nowTime.minusMinutes(5))) {
                pt.setStatus(PtLive.PtliveStatus.ACCESSIBLE);
            }

        }

    }


}
