package com.tcha.pt_class.mapper;

import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.pt_class.dto.PtClassDto;
import com.tcha.pt_class.entity.PtClass;
import com.tcha.pt_live.entity.PtLive.PtliveStatus;
import com.tcha.trainer.entity.Trainer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.tcha.user_profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PtClassMapper {

    List<PtClassDto.Response> classListToClassResponseDtoList(List<PtClass> classList);

    default PtClass classPostDtoToClass(Trainer trainer, LocalDate date, LocalTime time) {
        return PtClass.builder()
                .trainer(trainer)
                .startDate(date)
                .startTime(time)
                .build();
    }

    default PtClassDto.Response classToClassResponseDto(PtClass ptClass, PtliveStatus ptliveStatus, long reviewId, UserProfile userProfile , ExerciseLog.exerciseLogStatus exerciseLogStatus) {
        if (userProfile == null) {
            return PtClassDto.Response.builder()
                    .trainerId(ptClass.getTrainer().getId().toString())
                    .classId(ptClass.getId())
                    .liveId(ptClass.getPtLiveId())
                    .startDate(ptClass.getStartDate())
                    .startTime(ptClass.getStartTime())
                    .ptLiveStatus(ptliveStatus)
                    .reviewId(reviewId)
                    .trainerImage(ptClass.getTrainer().getUserProfile().getProfileImage())
                    .trainerName(ptClass.getTrainer().getUserProfile().getName())
                    .introduction(ptClass.getTrainer().getIntroduction())
                    .exerciseLogStatus(exerciseLogStatus)
                    .userName(null)
                    .userImage(null)
                    .build();
        }

        return PtClassDto.Response.builder()
                .trainerId(ptClass.getTrainer().getId().toString())
                .classId(ptClass.getId())
                .liveId(ptClass.getPtLiveId())
                .startDate(ptClass.getStartDate())
                .startTime(ptClass.getStartTime())
                .ptLiveStatus(ptliveStatus)
                .reviewId(reviewId)
                .trainerImage(ptClass.getTrainer().getUserProfile().getProfileImage())
                .trainerName(ptClass.getTrainer().getUserProfile().getName())
                .introduction(ptClass.getTrainer().getIntroduction())
                .userName(userProfile.getName())
                .userImage(userProfile.getProfileImage())
                .exerciseLogStatus(exerciseLogStatus)
                .build();
    }
}
