package com.tcha.exercise_log.controller;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.exercise_log.mapper.ExerciseLogMapper;
import com.tcha.exercise_log.service.ExerciseLogService;

import com.tcha.utils.pagination.MultiResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercise-logs")
@Validated
@Slf4j
@RequiredArgsConstructor
public class ExerciseLogController {
    private final ExerciseLogService exerciseLogService;
    private final ExerciseLogMapper exerciseLogMapper;
    @PostMapping
    public ResponseEntity postExerciseLog(@RequestBody ExerciseLogDto.Post postRequest) {
        ExerciseLog exerciseLogToService = exerciseLogMapper.postToExerciseLog(postRequest);
        ExerciseLog exerciseLogForResponse = exerciseLogService.createExerciseLog(exerciseLogToService);
        ExerciseLogDto.Response response = exerciseLogMapper.exerciseLogToResponse(exerciseLogForResponse);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getExerciseLogPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        Page<ExerciseLog> exerciseLogPage = exerciseLogService.findExerciseLogPages(page ,size);
        List<ExerciseLog> exerciseLogs = exerciseLogPage.getContent();
        List<ExerciseLogDto.Response> responses = exerciseLogMapper.exerciseLogsToResponses(exerciseLogs);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, exerciseLogPage),HttpStatus.OK);
    }

    @GetMapping("/{exercise-log-id}")
    public ResponseEntity getOneExerciseLog(@PathVariable(value = "exercise-log-id") Long id) {
        ExerciseLog exerciseLogForResponse = exerciseLogService.findExerciseLog(id);
        ExerciseLogDto.Response response = exerciseLogMapper.exerciseLogToResponse(exerciseLogForResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PatchMapping("/{exercise-log-id}")
    public ResponseEntity patchExerciseLog(@PathVariable("exercise-log-id") Long id, @RequestBody ExerciseLogDto.Patch patchRequest) {
        ExerciseLog exerciseLogToService = exerciseLogMapper.patchToExerciseLog(patchRequest);
        ExerciseLog exerciseLogForResponse = exerciseLogService.updateExerciseLog(exerciseLogToService);
        ExerciseLogDto.Response response = exerciseLogMapper.exerciseLogToResponse(exerciseLogForResponse);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/{exercise-log-id}")
    public ResponseEntity deleteOneExerciseLog(@PathVariable(value = "exercise-log-id") Long id) {
        exerciseLogService.deleteExerciseLog(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
