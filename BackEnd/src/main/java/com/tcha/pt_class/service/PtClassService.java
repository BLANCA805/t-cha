package com.tcha.pt_class.service;

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
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PtClassService {

    private final PtClassRepository ptClassRepository;
    private final TrainerRepository trainerRepository;
    private final PtLiveRepository ptLiveRepository;
    private final UserRepository userRepository;
    private final PtClassMapper ptClassMapper;


    public List<PtClassDto.Response> createPtClass(PtClassDto.Post postRequest) {

        // 요청을 보낸 트레이너가 유효한 트레이너인지 확인 (에러 핸들링 추가)
        Trainer trainer = trainerRepository.findById(
                UUID.fromString(postRequest.getTrainerId())).orElseThrow();

        // pt 수업 등록할 날짜
        LocalDate date = postRequest.getDate();

        // 반환값으로 보낼 데이터 (생성한 수업 리스트)
        List<PtClass> classList = new ArrayList<>();

        // 입력된 시간들에 대해서 ptClass 생성 (시간에 대한 유효성 검증 추가 -> 프론트에서 한대요)
        for (LocalTime time : postRequest.getStartTimeList()) {
            classList.add(ptClassRepository.save(
                    ptClassMapper.classPostDtoToClass(trainer, date, time)));
        }

        return ptClassMapper.classListToClassResponseDtoList(classList);
    }

    public List<PtClassDto.Response> findPtClassByTrainer(String trainerId) {

        // 요청을 보낸 트레이너가 유효한 트레이너인지 확인
        Trainer trainer = trainerRepository.findById(UUID.fromString(trainerId)).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.TRAINER_NOT_FOUND));

        List<PtClass> trainerClassList = ptClassRepository.findClassByTrainer(trainer.getId());

        return ptClassMapper.classListToClassResponseDtoList(trainerClassList);
    }

    public List<PtClassDto.Response> findPtClassByUser(long userProfileId) {

        // 유저 유효성 검사 (추가하기)

        // 유저가 진행한 라이브 데이터 가져오기
        List<PtLive> userLiveList = ptLiveRepository.findAllByUserProfile(userProfileId);
        List<PtClass> userClassList = new ArrayList<>();

        // 라이브 객체를 통해 클래스 객체 가져와서 리스트에 담기
        for (PtLive ptLive : userLiveList) {
            userClassList.add(ptClassRepository.findById(ptLive.getPtClassId()).orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.PT_CLASS_NOT_FOUND)));
        }

        return ptClassMapper.classListToClassResponseDtoList(userClassList);
    }

    public PtClassDto.Response updatePtClass(PtClassDto.Patch patchRequest) {

        // user 객체 가져오기 (유효성 검증 로직 추가 :: 활성상태 유저인지 확인, 일반 유저인지 확인)
        User user = userRepository.findById(patchRequest.getUserId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        // ptClass 객체 가져오기
        PtClass ptClass = ptClassRepository.findVerifiedClassById(patchRequest.getPtClassId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PT_CLASS_NOT_FOUND));

        // 예약 신청인지 취소인지 판단
        Long ptLiveId = ptClass.getPtLiveId();
        // 1. 수업 예약 신청이라면 (저장된 라이브 아이디 없음 = null)
        if (ptLiveId == null) {
            // 결제 로직

            // 결제 성공 시, 새로운 ptLive 객체 생성해서 DB insert
            PtLive createdPtLive = ptLiveRepository.save(
                    PtLive.builder()
                            .ptClassId(ptClass.getId())
                            .userProfile(user.getUserProfile())
                            .trainerId(ptClass.getTrainer().getId().toString())
                            .build());

            // ptClass에 ptLive 아이디 추가해주기 (update)
            ptClass.setPtLiveId(createdPtLive.getId());

            return ptClassMapper.classToClassResponseDto(ptClass);
        }

        // 2. 수업 예약 취소라면,
        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.PT_LIVE_NOT_FOUND));

        // 취소 요청을 보낸 유저가 예약한 유저와 일치하는지 확인
        if (user.getUserProfile().equals(ptLive.getUserProfile())) {
            ptClass.setPtLiveId(null);
            ptLive.setUserProfile(null);
        }

        return ptClassMapper.classToClassResponseDto(ptClass);
    }

//    public List<PtClassDto.Response> findPtClassByDatetime(PtClassDto.Get getRequest) {
//
//        LocalDate date = getRequest.getDate();
//        LocalTime fromTime = getRequest.getFromTime();
//        LocalTime toTime = getRequest.getToTime();
//
//        // 1. 날짜만 선택한 경우 -> 해당 날짜의 수업만 검색 -> where로 날짜 필터링
//        // 2. 시간만 선택한 경우 -> 오늘 이후 날짜의 해당 시간(구간) 수업만 검색
//        // 3. 둘 다 선택한 경우 -> 해당 날짜의 해당 시간(구간)의 수업만 검색
//
//        LocalDateTime from, to = null;
//        List<PtClass> datetimeClassList;
//
//        if (date == null) { // 시간만 선택한 경우
//            //fromTime과 현재 시간 비교, 더 미래 시간으로 from 설정
//            from = LocalDateTime.of(LocalDate.now(), fromTime).isAfter(LocalDateTime.now())
//                    ? LocalDateTime.of(LocalDate.now(), fromTime) : LocalDateTime.now();
//            to = LocalDateTime.of(LocalDate.now(), toTime);
//            datetimeClassList = ptClassRepository.findClassByTime(from, to);
//        } else {
//            from = LocalDateTime.of(date, fromTime);
//            to = LocalDateTime.of(date, toTime);
//            datetimeClassList = ptClassRepository.findClassByDate(from, to);
//        }
//
//        return ptClassMapper.classListToClassResponseDtoList(datetimeClassList);
//    }


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

        return ptClassMapper.classToClassResponseDto(ptClass);
    }

}
