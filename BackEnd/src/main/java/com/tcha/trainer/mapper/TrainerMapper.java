package com.tcha.trainer.mapper;

import com.tcha.trainer.dto.TrainerDto.Patch;
import com.tcha.trainer.dto.TrainerDto.Response;
import com.tcha.trainer.entity.Trainer;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    Trainer trainerPatchDtoToTrainer(Patch patchRequest);

    default Response trainerToResponseDto(Trainer trainer) {
        return Response.builder()
                .introduction(trainer.getIntroduction())
                .tags(trainer.getTags())
                .title(trainer.getTitle())
                .content(trainer.getContent())
                .profileImg(trainer.getUserProfile().getProfileImage())
                .profileName(trainer.getUserProfile().getName())
                .build();
    }

}
