package com.tcha.exercise_log.controller;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.exercise_log.mapper.ExerciseLogMapper;
import com.tcha.exercise_log.service.ExerciseLogService;

import com.tcha.question.dto.QuestionDto;
import com.tcha.question.entity.Question;
import com.tcha.utils.pagination.MultiResponseDto;
import com.tcha.utils.upload.service.S3Uploader;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/exercise-logs")
@Validated
@Slf4j
@RequiredArgsConstructor
public class ExerciseLogController {

    private final ExerciseLogService exerciseLogService;
    private final ExerciseLogMapper exerciseLogMapper;

    @PostMapping("/{pt-live-id}")
    public ResponseEntity postExerciseLog(
            @PathVariable(value = "pt-live-id") Long ptLiveId,
            @RequestBody ExerciseLogDto.Post postRequest
    ) throws IOException {

        ExerciseLog exerciseLogToService = exerciseLogMapper.postToExerciseLog(postRequest);

        ExerciseLog exerciseLogForResponse = exerciseLogService.createExerciseLog(
                exerciseLogToService, ptLiveId);
        ExerciseLogDto.Response response = exerciseLogMapper.exerciseLogToResponse(
                exerciseLogForResponse);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getExerciseLogPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        Page<ExerciseLog> exerciseLogPage = exerciseLogService.findExerciseLogPages(page, size);
        List<ExerciseLog> exerciseLogs = exerciseLogPage.getContent();
        List<ExerciseLogDto.Response> responses = exerciseLogMapper.exerciseLogsToResponses(
                exerciseLogs);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, exerciseLogPage),
                HttpStatus.OK);
    }

    @GetMapping("/{exercise-log-id}")
    public ResponseEntity getOneExerciseLog(@PathVariable(value = "exercise-log-id") Long id) {
        ExerciseLog exerciseLogForResponse = exerciseLogService.findExerciseLog(id);
        ExerciseLogDto.Response response = exerciseLogMapper.exerciseLogToResponse(
                exerciseLogForResponse);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("ptLive/{pt-live-id}")
    public ResponseEntity getOneExerciseLogByLiveId(@PathVariable(value = "pt-live-id") Long id) {
        ExerciseLog exerciseLogForResponse = exerciseLogService.findExerciseLogByLiveId(id);
        ExerciseLogDto.Response response = exerciseLogMapper.exerciseLogToResponse(
                exerciseLogForResponse);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PatchMapping("/{exercise-log-id}")
    public ResponseEntity patchExerciseLog(@PathVariable("exercise-log-id") Long id,
            @RequestBody ExerciseLogDto.Patch patchRequest
    ) throws IOException {

        ExerciseLog exerciseLogToService = exerciseLogMapper.patchToExerciseLog(patchRequest);
        ExerciseLog exerciseLogForResponse = exerciseLogService.updateExerciseLog(
                exerciseLogToService, id);
        ExerciseLogDto.Response response = exerciseLogMapper.exerciseLogToResponse(
                exerciseLogForResponse);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/{exercise-log-id}")
    public ResponseEntity deleteOneExerciseLog(@PathVariable(value = "exercise-log-id") Long id) {
        exerciseLogService.deleteExerciseLog(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
