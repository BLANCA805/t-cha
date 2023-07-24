package com.tcha.trainer.mapper;

import com.tcha.trainer.dto.TrainerDto;
import com.tcha.trainer.entity.Trainer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    Trainer trainerPatchDtoToTrainer(TrainerDto.RequestPatch patchRequest);

    TrainerDto.ResponseInfo trainerToTrainerInfoDto(Trainer trainer);

}
