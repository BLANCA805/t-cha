package com.tcha.trainer.service;

import com.tcha.trainer.dto.TrainerDto.RequestPatch;
import com.tcha.trainer.dto.TrainerDto.RequestSearch;
import com.tcha.trainer.dto.TrainerDto.ResponseInfo;
import com.tcha.trainer.dto.TrainerDto.ResponseList;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.mapper.TrainerMapper;
import com.tcha.trainer.repository.TrainerRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    public ResponseInfo updateTrainer(UUID trainerId, RequestPatch trainer) {

        Trainer origin = trainerRepository.getById(trainerId);

        origin.setIntroduction(trainer.getIntroduction());
        origin.setTitle(trainer.getTitle());
        origin.setContent(trainer.getContent());
        origin.setTags(trainer.getTags());
        // user 변경되지 않도록 설정하기

    }

    public ResponseInfo findOneTrainer(UUID trainerId) {

    }

    public ResponseList findAllTrainers() {
    }

    public void deleteTrainer(UUID trainerId) {
    }

    public ResponseList findTrainers(RequestSearch search) {
    }
}
