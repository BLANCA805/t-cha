package com.tcha.trainer.controller;

import com.tcha.trainer.dto.TrainerDto;
import com.tcha.trainer.dto.TrainerDto.RequestPatch;
import com.tcha.trainer.dto.TrainerDto.RequestSearch;
import com.tcha.trainer.dto.TrainerDto.ResponseInfo;
import com.tcha.trainer.dto.TrainerDto.ResponseList;
import com.tcha.trainer.service.TrainerService;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainers")
@Validated
@Slf4j
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    /*
    트레이너 아이디를 통해 트레이너 정보를 수정한다.
     */
    @PatchMapping("/{trainer-id}")
    public ResponseEntity<ResponseInfo> patchTrainer(
            @PathVariable("trainer-id") UUID trainerId,
            @RequestBody RequestPatch trainer) {

        ResponseInfo updatedTrainer = trainerService.updateTrainer(trainerId, trainer);

        return ResponseEntity.ok().body(updatedTrainer);
    }

    /*
    트레이너 아이디를 통해 트레이너 한 명의 정보를 조회한다.
     */
    @GetMapping("/{trainer-id}")
    public ResponseEntity<ResponseInfo> getOneTrainer(
            @PathVariable("trainer-id") UUID trainerId) {

        ResponseInfo trainer = trainerService.findOneTrainer(trainerId);

        return ResponseEntity.ok().body(trainer);
    }

    /*
    등록된 전체 트레이너의 정보를 조회한다.
     */
    @GetMapping
    public ResponseEntity<ResponseList> getAllTrainers() {

        ResponseList trainerList = trainerService.findAllTrainers();

        return ResponseEntity.ok().body(trainerList);
    }

    /*
    트레이너 아이디를 통해 트레이너를 삭제한다.
     */
    @DeleteMapping("{trainer-id}")
    public ResponseEntity deleteTrainer(@PathVariable("trainer-id") UUID trainerId) {

        trainerService.deleteTrainer(trainerId);

        return (ResponseEntity) ResponseEntity.noContent();
    }

    /*
    키워드(트레이너 이름 또는 태그)로 트레이너를 검색한다.
    유저가 선택한 날짜와 시간에 수업 가능한 트레이너를 검색한다.
     */
    @GetMapping("/search")
    public ResponseEntity<ResponseList> searchTrainer(@RequestBody RequestSearch search) {

        ResponseList searchResult = trainerService.findTrainers(search);

        return ResponseEntity.ok(searchResult);
    }
}
