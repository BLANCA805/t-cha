package com.tcha.exercise_log.controller;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.exercise_log.mapper.ExerciseLogMapper;
import com.tcha.exercise_log.service.ExerciseLogService;
import com.tcha.utils.pagination.MultiResponseDto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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

//    //운동일지 생성 -> 클라이언트에서 요청 X, 테스트용
//    @PostMapping("/{pt-live-id}")
//    public ResponseEntity postExerciseLog(
//            @PathVariable(value = "pt-live-id") @Positive Long ptLiveId,
//            @Valid @RequestBody ExerciseLogDto.Post postRequest
//    ) {
//        //이미 운동일지가 생성되어 있는지 체크, 에러(코드: 409) 없다면 아래의 코드들 실행
//        exerciseLogService.duplicateVerifiedByPtLiveId(ptLiveId);
//
//        //postRequest -> entity로 변경, 초기 상태: write
//        ExerciseLog exerciseLogToService = exerciseLogMapper.postToExerciseLog(postRequest);
//
//        //새로운 운동일지 생성
//        ExerciseLogDto.Response response = exerciseLogService.createExerciseLog(
//                exerciseLogToService, ptLiveId);
//
//        return new ResponseEntity(response, HttpStatus.CREATED);
//    }

    //운동일지 수정하기
    @PatchMapping("/{exercise-log-id}")
    public ResponseEntity patchExerciseLog(
            @PathVariable("exercise-log-id") @Positive Long exerciseLogId,
            @Valid @RequestBody ExerciseLogDto.Patch patchRequest
    ) {

        //존재하는 운동일지인지 체크
        ExerciseLog exerciseLog = exerciseLogService.findVerifiedById(exerciseLogId);

        //운동일지 내용 업데이트
        ExerciseLogDto.Response response = exerciseLogService.updateExerciseLog(
                exerciseLog, patchRequest);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    //운동일지 리스트 가져오기
    @GetMapping
    public ResponseEntity getExerciseLogPage(
            @RequestParam(value = "page", defaultValue = "1") @Positive Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {

        return new ResponseEntity<>(exerciseLogService.findExerciseLogPages(page, size),
                HttpStatus.OK);
    }

    //운동일지 1개 가져오기
    @GetMapping("/{exercise-log-id}")
    public ResponseEntity getOneExerciseLog(
            @PathVariable(value = "exercise-log-id") @Positive Long exerciseLogId) {

        ExerciseLogDto.Response response = exerciseLogService.findExerciseLog(exerciseLogId);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    //pt live id로 운동일지 1개 가져오기 (혹시 라이브로 접근할 경우, 사용)
    @GetMapping("ptLive/{pt-live-id}")
    public ResponseEntity getOneExerciseLogByLiveId(
            @PathVariable(value = "pt-live-id") @Positive Long exerciseLogId) {
        ExerciseLogDto.Response response = exerciseLogService.findExerciseLogByLiveId(
                exerciseLogId);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    //운동일지 삭제하기
    @DeleteMapping("/{exercise-log-id}")
    public ResponseEntity deleteOneExerciseLog(
            @PathVariable(value = "exercise-log-id") @Positive Long exerciseLogId) {
        exerciseLogService.deleteExerciseLog(exerciseLogId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //운동일지 작성 완료
    @PatchMapping("/done/{exercise-log-id}")
    public ResponseEntity<ExerciseLogDto.Response> patchWriteDoneExerciseLog(
            @PathVariable(value = "exercise-log-id") @Positive Long exerciseLogId,
            @Valid @RequestBody ExerciseLogDto.Patch patchRequest
    ) {
        //존재하는 운동일지인지 체크
        ExerciseLog exerciseLog = exerciseLogService.findVerifiedById(exerciseLogId);

        ExerciseLogDto.Response response = exerciseLogService.patchWriteDoneExerciseLog(
                exerciseLog, patchRequest);

        return ResponseEntity.ok().body(response);
    }
}
