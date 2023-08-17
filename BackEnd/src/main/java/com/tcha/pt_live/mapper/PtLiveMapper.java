package com.tcha.pt_live.mapper;

import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.pt_live.dto.PtLiveDto;
import com.tcha.pt_live.entity.PtLive;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PtLiveMapper {

    default PtLiveDto.Response ptLiveToResponseDto(PtLive ptLive, Trainer trainer,
            UserProfile userProfile, ExerciseLog.exerciseLogStatus exerciseLogStatus) {
        return PtLiveDto.Response.builder()
                .ptLiveId(ptLive.getId())
                .ptLiveStatus(ptLive.getStatus().toString())
                .ptClassId(ptLive.getPtClassId())
                .trainerId(trainer.getId())
                .trainerName(trainer.getUserProfile().getName())
                .trainerProfileImage(trainer.getUserProfile().getProfileImage())
                .userId(userProfile.getUser().getId())
                .userName(userProfile.getName())
                .userProfileImage(userProfile.getProfileImage())
                .exerciseLogStatus(exerciseLogStatus.toString())
                .build();
    }

}
