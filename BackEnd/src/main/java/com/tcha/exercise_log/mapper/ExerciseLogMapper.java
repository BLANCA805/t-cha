package com.tcha.exercise_log.mapper;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import com.tcha.exercise_log.dto.ExerciseLogDto.Response;
import com.tcha.exercise_log.entity.ExerciseLog;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseLogMapper {


    //운동일지 내용 업데이트: patchdto -> entity
//    ExerciseLog patchToExerciseLog(ExerciseLogDto.Patch patchRequest);

    default Response exerciseLogToResponse(ExerciseLog exerciseLog,
                                           String trainerName) {
        return Response.builder()
                .id(exerciseLog.getId())
                .title(exerciseLog.getTitle())
                .contents(exerciseLog.getContents())
                .images(exerciseLog.getImages())
                .videos(exerciseLog.getVideos())
                .profileName(exerciseLog.getPtLive().getUserProfile().getName())
                .trainerName(trainerName)
                .status(exerciseLog.getStatus())
                .build();
    }

    //운동일지 등록 postdto -> entity로 변경
//    default ExerciseLog postToExerciseLog(ExerciseLogDto.Post postRequest) {
//        return ExerciseLog.builder()
//                .title(postRequest.getTitle())
//                .content(postRequest.getContent())
//                .images(postRequest.getImages())
//                .videos(postRequest.getVideos())
//                .status(ExerciseLog.exerciseLogStaus.WRITE)
//                .build();
//    }

    List<Response> exerciseLogsToResponses(List<ExerciseLog> exerciseLogs);
}
