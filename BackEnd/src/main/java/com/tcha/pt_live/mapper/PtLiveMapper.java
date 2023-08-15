package com.tcha.pt_live.mapper;


import com.tcha.pt_live.dto.PtLiveDto;
import com.tcha.pt_live.entity.PtLive;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PtLiveMapper {

    default PtLiveDto.Response ptLiveToResponseDto(PtLive ptLive) {
        return PtLiveDto.Response.builder()
                .trainerId(ptLive.getPtClass().getTrainer().getId().toString())
                .trainerProfileImage(
                        ptLive.getPtClass().getTrainer().getUserProfile().getProfileImage())
//                .trainerName(ptLive.getPtClass().getTrainerName())
                .trainerName(ptLive.getPtClass().getTrainer().getUserProfile().getName())
                .startAt(ptLive.getPtClass().getStartAt())
                .closeAt(ptLive.getPtClass().getCloseAt())
                .price("7,000원")
                .build();
    }

}
