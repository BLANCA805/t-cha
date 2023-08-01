package com.tcha.test.controller;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.exercise_log.mapper.ExerciseLogMapper;
import com.tcha.exercise_log.service.ExerciseLogService;
import com.tcha.utils.upload.service.S3Uploader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
@Validated
@Slf4j
@RequiredArgsConstructor
public class testController {

    private final testService testService;
    private final ExerciseLogMapper exerciseLogMapper;
    private final S3Uploader s3Uploader;
    @PostMapping()
    public ResponseEntity postTest(
            @RequestBody ExerciseLogDto.Post postRequest
    ) throws IOException {

        ExerciseLog exerciseLogToService = testMapper.postToTest(postRequest);

        ExerciseLog exerciseLogForResponse = exerciseLogService.createExerciseLog(
                exerciseLogToService, imgPaths, videoPaths);
        ExerciseLogDto.Response response = exerciseLogService.getExerciseLog(exerciseLogForResponse.getId());

        return new ResponseEntity(response, HttpStatus.CREATED);
    }
}
