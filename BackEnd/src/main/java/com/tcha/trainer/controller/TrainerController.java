package com.tcha.trainer.controller;

import com.tcha.trainer.dto.TrainerDto;
import com.tcha.trainer.service.TrainerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainers")
@Validated
@Slf4j
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    /**
     * 프로필 아이디를 통해 트레이너를 등록한다.
     *
     * @param userProfileId 트레이너를 등록을 요청한 유저의 프로필 아이디
     * @param postRequest   트레이너 정보(한 줄 소개, 제목, 내용, 태그)
     */
    @PostMapping("/{user-profile-id}")
    public ResponseEntity<TrainerDto.Response> postTrainer(
            @PathVariable("user-profile-id") long userProfileId,
            @RequestBody TrainerDto.Post postRequest) {

        TrainerDto.Response createdTrainer =
                trainerService.createTrainer(userProfileId, postRequest);

        return new ResponseEntity<>(createdTrainer, HttpStatus.CREATED);
    }

    /*
    트레이너 아이디를 통해 트레이너 정보를 수정한다.
     */
    @PatchMapping("/{trainer-id}")
    public ResponseEntity<TrainerDto.Response> patchTrainer(
            @PathVariable("trainer-id") String trainerId,
            @RequestBody TrainerDto.Patch patchRequest) {

        TrainerDto.Response updatedTrainer = trainerService.updateTrainer(trainerId, patchRequest);

        return ResponseEntity.ok().body(updatedTrainer);
    }

    /*
    트레이너 아이디를 통해 트레이너 한 명의 정보를 조회한다.
     */
    @GetMapping("/{trainer-id}")
    public ResponseEntity<TrainerDto.Response> getOneTrainer(
            @PathVariable("trainer-id") String trainerId) {

        TrainerDto.Response trainer = trainerService.findOneTrainer(trainerId);

        return ResponseEntity.ok().body(trainer);
    }

    /*
    등록된 전체 트레이너의 정보를 조회한다.
    트레이너 목록 페이지 요청 API
     */
    @GetMapping
    public ResponseEntity<List<TrainerDto.ResponseList>> getAllTrainers() {

        List<TrainerDto.ResponseList> trainerList = trainerService.findAllTrainers();

        return ResponseEntity.ok().body(trainerList);
    }

    /*
    트레이너 아이디를 통해 트레이너를 삭제한다.
     */
    @DeleteMapping("/{trainer-id}")
    public ResponseEntity<?> deleteTrainer(@PathVariable("trainer-id") String trainerId) {

        trainerService.deleteTrainer(trainerId);

        return (ResponseEntity<?>) ResponseEntity.noContent();
    }

    /*
    키워드(트레이너 이름 또는 태그)로 트레이너를 검색한다.
    유저가 선택한 날짜와 시간에 수업 가능한 트레이너를 검색한다.
     */
    @GetMapping("/search")
    public ResponseEntity<List<TrainerDto.ResponseList>> searchTrainer(
            @RequestBody TrainerDto.Get getRequest) {

        List<TrainerDto.ResponseList> searchResult = trainerService.findTrainers(getRequest);

        return ResponseEntity.ok(searchResult);
    }
}
