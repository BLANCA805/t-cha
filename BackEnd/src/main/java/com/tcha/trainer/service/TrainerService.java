package com.tcha.trainer.service;

import com.tcha.tag.entity.Tag;
import com.tcha.tag.repository.TagRepository;
import com.tcha.trainer.dto.TrainerDto;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.mapper.TrainerMapper;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
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
public class TrainerService {

    private final UserProfileRepository userProfileRepository;
    private final TagRepository tagRepository;
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    public TrainerDto.Response createTrainer(Long userProfileId, TrainerDto.Post postRequest) {

        // userProfile 객체 가져오기 (유효성 검증 로직 추가 :: 활성상태 유저인지 확인, 일반 유저인지 확인)
        UserProfile postUser = userProfileRepository.findById(userProfileId).orElseThrow();

        // 트레이너 생성
        Trainer createdTrainer = trainerRepository.save(
                trainerMapper.trainerPostDtoToTrainer(postRequest, postUser));

        // 태그 테이블 설정
        String trainerStr =
                createdTrainer.getId().toString() + ","; // 태그 trainers에 추가할 트레이너 아이디 문자열
        String[] tagList = postRequest.getTags().split(",");
        for (String t : tagList) {
            // 존재하지 않는 태그일 경우, 이름만 가지고 있는 새로운 태그 엔티티 생성
            Tag tag = tagRepository.findByName(t).orElseGet(() -> Tag.builder().name(t).build());
            tag.setTrainers(tag.getTrainers() + trainerStr);
            Tag createdTag = tagRepository.save(tag);
            log.debug("[TrainerService] createTrainer :: 트레이너 생성 시 생성/수정되는 태그 정보 = {} ",
                    createdTag);
        }

        // 트레이너 이미지 테이블 설정

        return trainerMapper.trainerToResponseDto(createdTrainer);
    }


    public TrainerDto.Response updateTrainer(String trainerId, TrainerDto.Patch patchRequest) {

        Trainer trainer = trainerRepository.findById(UUID.fromString(trainerId)).orElseThrow();

        trainer.setIntroduction(patchRequest.getIntroduction());
        trainer.setTitle(patchRequest.getTitle());
        trainer.setContent(patchRequest.getContent());
        trainer.setTags(patchRequest.getTags());
        // user 변경되지 않도록(set XX) 설정하기

        return trainerMapper.trainerToResponseDto(trainer);
    }

    public TrainerDto.Response findOneTrainer(String trainerId) {

        Trainer trainer = trainerRepository.findById(UUID.fromString(trainerId)).orElseThrow();

        return trainerMapper.trainerToResponseDto(trainer);
    }

    public List<TrainerDto.ResponseList> findAllTrainers() {

        // trainer 테이블
//        private String id; // 트레이너 id
//        private String introduction; // 트레이너 한 줄 소개
//        private String tags; // 트레이너 태그
//        private LocalDateTime createdAt; // 트레이너 등록일

        // user_profile 테이블 (트레이너 테이블의 user_profile_id로 접근)
//        private String profileName; // 유저 이름 (트레이너 이름)
//        private String profileImg; // 유저 프로필 사진 (트레이너 프사)

        // redis에서 전달되는 값
//        private float stars; // 트레이너 별점

        // pt_live 테이블 (트레이너 테이블의 user_profile_id로 접근)
//        private int userCount; // 누적 회원 수
//        private int ptCount; // 누적 예약 수
//        private int reviewCount; // 누적 리뷰 수
//        private int revisitGrade; // 재방문율에 따른 등급 (0(일반), 1(브론즈), 2(실버), 3(골드))

        // userProfile 객체 가져오기
//        UserProfile userProfile =
//                userProfileRepository.findById(postRequest.getUserProfileId()).get();

        List<TrainerDto.ResponseList> trainerList = new ArrayList<TrainerDto.ResponseList>();
//        for (Trainer t : trainerRepository.findAll()) {
//            trainerList.add(ResponseList.builder()
//                            .id(t.getId().toString())
//                            .introduction(t.getIntroduction())
//                            .tags(t.getTags())
//                            .createdAt(t.getCreatedAt())
//                    .profileName()
//                    .profileImg()
//                    .
//        }

        return trainerList;
    }

    public void deleteTrainer(String trainerId) {

        trainerRepository.deleteById(UUID.fromString(trainerId));
    }

    public List<Trainer> findTrainers(TrainerDto.Get search) {

        String keyword = "%" + search.getKeyword() + "%";

        // 태그로 검색
        List<Trainer> trainerList = trainerRepository.findByTagsLikeIgnoreCase(keyword);

        // 유저 이름으로 검색

        // 날짜, 시간으로 검샋

        return trainerList;
    }
}
