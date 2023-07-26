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


    ExerciseLogDto.Response exerciseLogToResponse(ExerciseLog exerciseLog);

    List<Response> exerciseLogsToResponses(List<ExerciseLog> exerciseLogs);
}
