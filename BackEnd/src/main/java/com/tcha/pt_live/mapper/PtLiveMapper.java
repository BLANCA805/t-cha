package com.tcha.pt_live.mapper;


import com.tcha.pt_live.dto.PtLiveDto;
import com.tcha.pt_live.entity.PtLive;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PtLiveMapper {

    default PtLiveDto.Response ptLiveToResponseDto(PtLive ptLive) {
        return PtLiveDto.Response.builder()
                .ptLiveId(ptLive.getId())
//                .trainerProfileImage()
//                .trainerName()
//                .startAt()
//                .closeAt()
                .price("7,000원")
                .build();
    }

}
