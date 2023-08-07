package com.tcha.pt_live.service;

import com.tcha.pt_class.entity.PtClass;
import com.tcha.pt_class.repository.PtClassRepository;
import com.tcha.pt_live.dto.PtLiveDto;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.pt_live.mapper.PtLiveMapper;
import com.tcha.pt_live.repository.PtLiveRepository;
import com.tcha.user.entity.User;
import com.tcha.user.repository.UserRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PtLiveService {

//    private final PtLiveRepository ptLiveRepository;
//    private final UserProfileRepository userProfileRepository;
//    private final PtClassRepository ptClassRepository;
//    private final PtLiveMapper ptLiveMapper;
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
//
//    public PtLiveDto.Response findOnePtLive(long ptLiveId) {
//
//        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow();
//
//        return ptLiveMapper.ptLiveToResponseDto(ptLive);
//    }
//
//    public void deletePtLive(long ptLiveId) {
//
//        ptLiveRepository.deleteById(ptLiveId);
//    }

}
