package com.tcha.pt_live.mapper;

import com.tcha.pt_live.dto.PtLiveDto;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PtLiveMapper {

    default PtLiveDto.Response ptLiveToResponseDto(PtLive ptLive, Trainer trainer,
            UserProfile userProfile) {
        return PtLiveDto.Response.builder()
                .ptLiveId(ptLive.getId())
                .status(ptLive.getStatus().toString())
                .ptClassId(ptLive.getPtClassId())
                .trainerId(trainer.getId())
                .trainerName(trainer.getUserProfile().getName())
                .trainerProfileImage(trainer.getUserProfile().getProfileImage())
                .userId(userProfile.getUser().getId())
                .userName(userProfile.getName())
                .userProfileImage(userProfile.getProfileImage())
                .build();
    }

}
