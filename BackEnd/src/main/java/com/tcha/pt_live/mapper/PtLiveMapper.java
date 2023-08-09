package com.tcha.pt_live.mapper;

import com.tcha.pt_class.entity.PtClass;
import com.tcha.pt_live.dto.PtLiveDto;
import com.tcha.pt_live.entity.PtLive;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PtLiveMapper {

//    default PtLiveDto.Response ptLiveToResponseDto(PtLive ptLive, PtClass ptClass) {
//        return PtLiveDto.Response.builder()
//                .ptLiveId(ptLive.getId())
//                .ptClassId(ptClass.getId())
//                .startAt(ptClass.getStartAt())
//                .closeAt(ptClass.getCloseAt())
////                .price(ptClass.getPrice())
//                .price("7,000원")
//                .trainerId(ptLive.getTrainerId())
//                .trainerProfileImage(ptClass.getTrainer().getUserProfile().getProfileImage())
//                .trainerName(ptClass.getTrainer().getUserProfile().getName())
//                .build();
//    }

}
