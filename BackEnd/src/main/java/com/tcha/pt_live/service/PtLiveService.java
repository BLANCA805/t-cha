package com.tcha.pt_live.service;

import com.tcha.pt_class.entity.PtClass;
import com.tcha.pt_class.repository.PtClassRepository;
import com.tcha.pt_live.dto.PtLiveDto;
import com.tcha.pt_live.dto.PtLiveDto.Response;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.pt_live.mapper.PtLiveMapper;
import com.tcha.pt_live.repository.PtLiveRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import java.util.ArrayList;
import java.util.List;
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
    private final UserProfileRepository userProfileRepository;
    private final PtClassRepository ptClassRepository;
    private final PtLiveMapper ptLiveMapper;

    public PtLiveDto.Response createPtLive(long ptClassId, long userProfileId) {

        // userProfile 객체 가져오기 (유효성 검증 로직 추가 :: 활성상태 유저인지 확인, 일반 유저인지 확인)
        UserProfile userProfile = userProfileRepository.findById(userProfileId).orElseThrow();

        // ptClass 객체 가져오기 (유효성 검증 로직 추가)
        PtClass ptClass = ptClassRepository.findById(ptClassId).orElseThrow();

        // 새로운 ptLive 객체 생성해서 DB insert
        PtLive createdPtLive = ptLiveRepository.save(
                PtLive.builder()
                        .ptClass(ptClass)
                        .userProfile(userProfile)
                        .build());

        return ptLiveMapper.ptLiveToResponseDto(createdPtLive);
    }

    public boolean updatePtLive(long ptLiveId, long userProfileId) {

        // 수정할 라이브 객체 가져오기
        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow();

        // ptLive 객체에 저장된 유저 정보 삭제
        ptLive.setUserProfile(null);

        return true;
    }

    public PtLiveDto.Response findOnePtLive(long ptLiveId) {

        PtLive ptLive = ptLiveRepository.findById(ptLiveId).orElseThrow();

        return ptLiveMapper.ptLiveToResponseDto(ptLive);
    }

    public void deletePtLive(long ptLiveId) {

        ptLiveRepository.deleteById(ptLiveId);
    }
}
