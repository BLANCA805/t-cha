package com.tcha.trainer.mapper;

import com.tcha.trainer.dto.TrainerDto;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user.entity.User;
import com.tcha.user_profile.dto.UserProfileDto;
import com.tcha.user_profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    // mapper에서 다른 entity 가져오는거 괜찮은지 검색해보기
    default Trainer trainerPostDtoToTrainer(TrainerDto.Post postRequest) {

        UserProfile userProfile = new UserProfile();
        userProfile.setId(postRequest.getUserProfileId());

        return Trainer.builder()
                .userProfile(userProfile)
                .introduction(postRequest.getIntroduction())
                .tags(postRequest.getTags())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();

    }

    default TrainerDto.Response trainerToResponseDto(Trainer trainer) {

        return TrainerDto.Response.builder()
                .id(trainer.getId())
                .introduction(trainer.getIntroduction())
                .tags(trainer.getTags())
                .title(trainer.getTitle())
                .content(trainer.getContent())
                .profileImg(trainer.getUserProfile().getProfileImage())
                .profileName(trainer.getUserProfile().getName())
                .build();

    }
}
