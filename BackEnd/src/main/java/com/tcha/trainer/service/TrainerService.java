package com.tcha.trainer.service;

import com.tcha.trainer.dto.TrainerDto.Patch;
import com.tcha.trainer.dto.TrainerDto.Post;
import com.tcha.trainer.dto.TrainerDto.Get;
import com.tcha.trainer.dto.TrainerDto.Response;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.mapper.TrainerMapper;
import com.tcha.trainer.repository.TrainerRepository;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.repository.UserProfileRepository;
import java.util.List;
import java.util.Optional;
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

    private final TrainerRepository trainerRepository;
    private final UserProfileRepository userProfileRepository;
    private final TrainerMapper trainerMapper;

    public Response createTrainer(Post postRequest) {

        // userProfile 객체 가져오기
        UserProfile userProfile = userProfileRepository.findById(postRequest.getUserProfileId())
                .get();

        Trainer createdTrainer = trainerRepository.save(
                Trainer.builder().title(postRequest.getTitle())
                        .content(postRequest.getContent())
                        .tags(postRequest.getTags())
                        .introduction(postRequest.getIntroduction())
                        .userProfile(userProfile)
                        .build());

        return trainerMapper.trainerToResponseDto(createdTrainer);
    }


    public Response updateTrainer(String trainerId, Patch patchRequest) {

        Trainer target = trainerRepository.findById(trainerId);

        target.setIntroduction(patchRequest.getIntroduction());
        target.setTitle(patchRequest.getTitle());
        target.setContent(patchRequest.getContent());
        target.setTags(patchRequest.getTags());
        // user 변경되지 않도록(set XX) 설정하기

        return trainerMapper.trainerToResponseDto(target);
    }

    public Response findOneTrainer(String trainerId) {

        return trainerMapper.trainerToResponseDto(trainerRepository.findById(trainerId));
    }

    public List<Trainer> findAllTrainers() {

        List<Trainer> trainerList = trainerRepository.findAll();

        return trainerList;
    }

    public void deleteTrainer(String trainerId) {

        trainerRepository.deleteById(trainerId);

    }

    public List<Trainer> findTrainers(Get search) {

        String keyword = "%" + search.getKeyword() + "%";

        // 태그로 검색
        List<Trainer> trainerList = trainerRepository.findByTagsLikeIgnoreCase(keyword);

        // 유저 이름으로 검색

        // 날짜, 시간으로 검샋

        return trainerList;
    }
}
