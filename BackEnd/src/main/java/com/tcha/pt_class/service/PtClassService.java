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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        // 입력된 시간들에 대해서 ptClass 생성 (시간에 대한 유효성 검증 추가)
        for (LocalTime startTime : postRequest.getStartTimeList()) {
            ptClassRepository.save(
                    ptClassMapper.classPostDtoToClass(trainer, date.atTime(startTime)));
        }

        // 반환값으로 보낼 데이터 (선택된 날짜의 모든 수업)
        List<PtClass> classList =
                ptClassRepository.findClassByTrainerAndDate(trainer.getId(), date);

        return ptClassMapper.classListToclassResponseDtoList(classList);
    }


    public PtClassDto.Response updatePtClass(PtClassDto.Patch patchRequest) {

        // user 객체 가져오기 (유효성 검증 로직 추가 :: 활성상태 유저인지 확인, 일반 유저인지 확인)
        User user = userRepository.findById(patchRequest.getUserId()).orElseThrow();

        // ptClass 객체 가져오기 (유효성 검증 로직 추가)
        PtClass ptClass = ptClassRepository.findById(patchRequest.getPtClassId()).orElseThrow();

        // 예약 신청인지 취소인지 판단
        Long ptLiveId = ptClass.getPtLiveId();
        // 1. 수업 예약 신청이라면 (저장된 라이브 아이디 없음)
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

            return null; // 신청한 수업에 대한 정보 반환
        }

        // 2. 수업 예약 취소라면
        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow();
        // 취소 요청을 보낸 유저가 예약한 유저와 일치하는지 확인
        if (user.getUserProfile().equals(ptLive.getUserProfile())) {
            ptClass.setPtLiveId(null);
            ptLive.setUserProfile(null);

            return null; // 예약 취소 성공에 대한 정보 반환
        }

        return ptClassMapper.classToClassResponseDto(ptClass);
    }


    public List<PtClassDto.Response> findPtClassByTrainer(String trainerId) {

        List<PtClass> trainerClassList =
                ptClassRepository.findClassByTrainer(UUID.fromString(trainerId));

        return ptClassMapper.classListToclassResponseDtoList(trainerClassList);
    }


    public List<PtClassDto.Response> findPtClassByDatetime(PtClassDto.Get getRequest) {

        LocalDate date = getRequest.getDate();
        LocalTime fromTime = getRequest.getFromTime();
        LocalTime toTime = getRequest.getToTime();

        // 1. 날짜만 선택한 경우 -> 해당 날짜의 수업만 검색 -> where로 날짜 필터링
        // 2. 시간만 선택한 경우 -> 오늘 이후 날짜의 해당 시간(구간) 수업만 검색
        // 3. 둘 다 선택한 경우 -> 해당 날짜의 해당 시간(구간)의 수업만 검색

        LocalDateTime from, to = null;
        List<PtClass> datetimeClassList;

        if (date == null) { // 시간만 선택한 경우
            //fromTime과 현재 시간 비교, 더 미래 시간으로 from 설정
            from = LocalDateTime.of(LocalDate.now(), fromTime).isAfter(LocalDateTime.now())
                    ? LocalDateTime.of(LocalDate.now(), fromTime) : LocalDateTime.now();
            to = LocalDateTime.of(LocalDate.now(), toTime);
            datetimeClassList = ptClassRepository.findClassByTime(from, to);
        } else {
            from = LocalDateTime.of(date, fromTime);
            to = LocalDateTime.of(date, toTime);
            datetimeClassList = ptClassRepository.findClassByDate(from, to);
        }

        return ptClassMapper.classListToclassResponseDtoList(datetimeClassList);
    }


    public List<PtClassDto.Response> deletePtClass(long ptClassId, String trainerId) {

        // 삭제하고자 하는 수업에 대한 유효성 검증
        PtClass ptClass = ptClassRepository.findById(ptClassId).orElseThrow();

        // 1. 요청 트레이너의 수업인지 확인 (다른 트레이너의 수업이면 삭제 불가)
        if (!ptClass.getTrainer().getId().toString().equals(trainerId)) {
            return null;
        }
        // 2. 이미 예약된 수업인지 확인 (예약된 상태라면 삭제 불가)
        if (ptClass.getPtLiveId() != null) {
            return null;
        }

        // 수업 삭제(삭제 상태값 컬럼 변경)
        ptClass.setIsDel(1);

        return null; // 리턴값 추후 수정
    }

//    public List<PtClassDto.Response> findAllPtClasses() {
//
//        List<PtClass> classList = ptClassRepository.findAll();
//
//        return ptClassMapper.classListToclassResponseDtoList(classList);
//    }

}
