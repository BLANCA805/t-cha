package com.tcha.trainer.controller;

import com.tcha.question.dto.QuestionDto;
import com.tcha.question.entity.Question;
import com.tcha.trainer.dto.TrainerDto;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.mapper.TrainerMapper;
import com.tcha.trainer.service.TrainerService;
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
@RequestMapping("/trainers")
@Validated
@Slf4j
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerMapper trainerMapper;
    private final TrainerService trainerService;

    /**
     * 유저 프로필 아이디를 통해 트레이너를 등록한다.
     *
     * @param userProfileId 트레이너를 등록을 요청한 유저의 프로필 아이디
     * @param postRequest   트레이너 정보(한 줄 소개, 제목, 내용, 태그)
     */
    @PostMapping("/{user-profile-id}")
    public ResponseEntity<TrainerDto.Response> postTrainer(
            @PathVariable("user-profile-id") long userProfileId,
            @RequestBody TrainerDto.Post postRequest) {

        Trainer trainerForService = trainerMapper.trainerPostDtoToTrainer(userProfileId, postRequest);
        Trainer trainerForResponse = trainerService.createTrainer(trainerForService);
        TrainerDto.Response response = trainerMapper.trainerToResponseDto(trainerForResponse);

        return new ResponseEntity<TrainerDto.Response>(response, HttpStatus.CREATED);
    }

    /**
     * 트레이너가 자신의 트레이너 정보를 수정한다.
     */
    @PatchMapping("/{trainer-id}")
    public ResponseEntity<TrainerDto.Response> patchTrainer(
            @PathVariable("trainer-id") String trainerId,
            @RequestBody TrainerDto.Patch patchRequest) {
        Trainer trainerForService = trainerMapper.patchToTrainer(patchRequest);
        Trainer trainerForResponse = trainerService.updateTrainer(trainerId, trainerForService);
        TrainerDto.Response response = trainerMapper.trainerToResponseDto(trainerForResponse);

        return new ResponseEntity<TrainerDto.Response>(response, HttpStatus.OK);
    }

    /**
     * 트레이너 아이디를 통해 트레이너 한 명의 정보를 조회한다. 트레이너 상세 페이지에서 보여지는 정보
     */
    @GetMapping("/{trainer-id}")
    public ResponseEntity<TrainerDto.Response> getOneTrainer(
            @PathVariable("trainer-id") String trainerId) {

        TrainerDto.Response trainer = trainerService.findOneTrainer(trainerId);

        return new ResponseEntity<TrainerDto.Response>(trainer, HttpStatus.OK);
    }

    /**
     * 등록된 전체 트레이너의 정보를 조회한다. 트레이너 목록 페이지에서 보여지는 정보
     */
    @GetMapping
    public ResponseEntity getAllQuestions(@RequestParam int page, @RequestParam int size) {

        Page<Trainer> pageTrainer = trainerService.findAllTrainers(page - 1, size);
        List<Trainer> memberListForResponse = pageTrainer.getContent();
        List<TrainerDto.Response> response = trainerMapper.trainersToTrainerList(
                memberListForResponse);

        return new ResponseEntity(new MultiResponseDto<>(response, pageTrainer), HttpStatus.OK);
    }

    /**
     * 트레이너 아이디를 통해 트레이너를 삭제한다. (트레이너에서 일반 유저로 강등)
     */
    @DeleteMapping("/{trainer-id}")
    public ResponseEntity<TrainerDto.Response> deleteTrainer(
            @PathVariable("trainer-id") String trainerId) {

        Trainer deletedTrainer = trainerService.deleteTrainer(trainerId);
        TrainerDto.Response response = trainerMapper.trainerToResponseDto(deletedTrainer);

        return new ResponseEntity<TrainerDto.Response>(response, HttpStatus.OK);
    }

    /**
     * 키워드(트레이너 이름 또는 태그)로 트레이너를 검색한다. 유저가 선택한 날짜와 시간에 수업 가능한 트레이너를 검색한다.
     */
    @GetMapping("/search")
    public ResponseEntity<List<TrainerDto.ResponseList>> searchTrainer(
            @RequestBody TrainerDto.Get getRequest) {

        List<TrainerDto.ResponseList> searchResult = trainerService.searchTrainers(getRequest);

        return new ResponseEntity<List<TrainerDto.ResponseList>>(searchResult, HttpStatus.OK);
    }
}
