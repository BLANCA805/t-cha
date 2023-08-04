package com.tcha.exercise_log.mapper;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import com.tcha.exercise_log.dto.ExerciseLogDto.Response;
import com.tcha.exercise_log.entity.ExerciseLog;

import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseLogMapper {

    ExerciseLog postToExerciseLog(ExerciseLogDto.Post postRequest);

    ExerciseLog patchToExerciseLog(ExerciseLogDto.Patch patchRequest);

    default ExerciseLogDto.Response exerciseLogToResponse(ExerciseLog exerciseLog) {
        return ExerciseLogDto.Response.builder()
                .id(exerciseLog.getId())
                .title(exerciseLog.getTitle())
                .content(exerciseLog.getContent())
                .images(exerciseLog.getImages())
                .videos(exerciseLog.getVideos())
                .profileName(exerciseLog.getPtLive().getUserProfile().getName())
                .trainerName(exerciseLog.getPtLive().getPtClass().getTrainer().getUserProfile().getName())
                .build();
    }

    ;

    List<Response> exerciseLogsToResponses(List<ExerciseLog> exerciseLogs);
}
