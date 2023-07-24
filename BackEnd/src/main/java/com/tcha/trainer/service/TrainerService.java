package com.tcha.trainer.service;

import com.tcha.trainer.dto.TrainerDto.RequestPatch;
import com.tcha.trainer.dto.TrainerDto.RequestSearch;
import com.tcha.trainer.dto.TrainerDto.ResponseInfo;
import com.tcha.trainer.dto.TrainerDto.ResponseList;
import com.tcha.trainer.entity.Trainer;
import com.tcha.trainer.mapper.TrainerMapper;
import com.tcha.trainer.repository.TrainerRepository;
import java.util.List;
import java.util.Optional;
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

        Trainer target = trainerRepository.findById(trainerId).get();

        target.setIntroduction(trainer.getIntroduction());
        target.setTitle(trainer.getTitle());
        target.setContent(trainer.getContent());
        target.setTags(trainer.getTags());
        // user 변경되지 않도록(set XX) 설정하기

        return trainerMapper.trainerToTrainerInfoDto(target);
    }

    public ResponseInfo findOneTrainer(UUID trainerId) {

        return trainerMapper.trainerToTrainerInfoDto(trainerRepository.findById(trainerId).get());
    }

    public ResponseList findAllTrainers() {

        List<Trainer> trainerList = trainerRepository.findAll();
        

    }

    public void deleteTrainer(UUID trainerId) {
    }

    public ResponseList findTrainers(RequestSearch search) {
    }
}
