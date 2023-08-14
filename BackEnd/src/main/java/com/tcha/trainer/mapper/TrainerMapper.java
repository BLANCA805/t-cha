package com.tcha.trainer.mapper;

import com.tcha.trainer.dto.TrainerDto;
import com.tcha.trainer.dto.TrainerDto.Response;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    List<TrainerDto.ResponseList> trainerListToResponseListDtoList(List<Trainer> trainerList);

    default Trainer trainerPostDtoToTrainer(Long userProfileId, TrainerDto.Post postRequest) {

        // userProfile Mapping
        UserProfile userProfile = new UserProfile();
        userProfile.setId(userProfileId);

        return Trainer.builder()
                .userProfile(userProfile)
                .introduction(postRequest.getIntroduction())
                .tags(postRequest.getTags())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .images(postRequest.getImages())
                .build();

    }

    Trainer patchToTrainer(TrainerDto.Patch patchRequest);

    default TrainerDto.Response trainerToResponseDto(Trainer trainer, List<Long> userProfileIdList) {

        return TrainerDto.Response.builder()
                .id(trainer.getId())
                .introduction(trainer.getIntroduction())
                .tags(trainer.getTags())
                .title(trainer.getTitle())
                .content(trainer.getContent())
                .images(trainer.getImages())
                .profileImg(trainer.getUserProfile().getProfileImage())
                .profileName(trainer.getUserProfile().getName())
                .userProfileIdList(userProfileIdList)
                .build();
    }

    default TrainerDto.ResponseList trainerToResponseListDto(Trainer trainer) {
        return TrainerDto.ResponseList.builder()
                .id(trainer.getId())
                .introduction(trainer.getIntroduction())
                .tags(trainer.getTags())
                .profileImg(trainer.getUserProfile().getProfileImage())
                .profileName(trainer.getUserProfile().getName())
                .stars(3.5)
                .reviewCount(14)
                .createdAt(trainer.getCreatedAt().toLocalDate())
                .ptCount(20)
                .revisitGrade(0)
                .bookmarkCount(3)
                .build();
    }

    List<TrainerDto.Response> trainersToTrainerList(List<Trainer> memberListForResponse);
}
