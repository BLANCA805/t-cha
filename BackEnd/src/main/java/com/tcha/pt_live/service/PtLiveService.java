package com.tcha.pt_live.service;

import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.exercise_log.repository.ExerciseLogRepository;
import com.tcha.pt_live.dto.PtLiveDto;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.pt_live.mapper.PtLiveMapper;
import com.tcha.pt_live.repository.PtLiveRepository;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user_profile.repository.UserProfileRepository;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import com.tcha.utils.exceptions.codes.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PtLiveService {

    private final PtLiveRepository ptLiveRepository;
    private final PtLiveMapper ptLiveMapper;
    private final UserProfileRepository userProfileRepository;
    private final TrainerRepository trainerRepository;
    private final ExerciseLogRepository exerciseLogRepository;


    //    private final PtClassRepository ptClassRepository;
//    private final UserRepository userRepository;
//
//
//    public PtLiveDto.Response createPtLive(PtLiveDto.Post postRequest) {
//
//        // user 객체 가져오기 (유효성 검증 로직 추가 :: 활성상태 유저인지 확인, 일반 유저인지 확인)
//        User user = userRepository.findById(postRequest.getUserId()).orElseThrow();
//
//        // ptClass 객체 가져오기 (유효성 검증 로직 추가)
//        PtClass ptClass = ptClassRepository.findById(postRequest.getPtClassId()).orElseThrow();
//
//        // 결제 로직 추가
//
//        // 결제 성공 시, 새로운 ptLive 객체 생성해서 DB insert
//        PtLive createdPtLive = ptLiveRepository.save(
//                PtLive.builder()
//                        .ptClassId(ptClass.getId())
//                        .userProfile(userProfile)
//                        .trainerId(ptClass.getTrainer().getId().toString())
//                        .build());
//
//        // ptClass에 ptLive 아이디 추가해주기 (update)
//        ptClass.setPtLiveId(createdPtLive.getId());
//
//        return ptLiveMapper.ptLiveToResponseDto(createdPtLive, ptClass);
//    }
//
//    public boolean updatePtLive(long ptLiveId, long userProfileId) {
//
//        // 수정할 라이브 객체 가져오기
//        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow();
//
//        // ptLive 객체에 저장된 유저 정보 삭제
//        ptLive.setUserProfile(null);
//
//        return true;
//    }

    public PtLiveDto.Response findOnePtLive(long ptLiveId) {

        // 라이브 객체 가져오기
        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.PT_LIVE_NOT_FOUND));

        Trainer trainer = trainerRepository.findById(ptLive.getTrainerId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.TRAINER_NOT_FOUND));

        ExerciseLog exerciseLog = exerciseLogRepository.findByLiveId(ptLiveId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.EXERCISELOG_NOT_FOUND));

        return ptLiveMapper.ptLiveToResponseDto(ptLive, trainer, ptLive.getUserProfile(), exerciseLog.getStatus());
    }

//    public void deletePtLive(long ptLiveId) {
//
//        ptLiveRepository.deleteById(ptLiveId);
//    }

}
