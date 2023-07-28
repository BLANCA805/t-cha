package com.tcha.guide.mapper;

import com.tcha.guide.dto.GuideDto.Response;
import com.tcha.guide.entity.Guide;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuideMapper {

    default Response guideToResponseDto(Guide guide) {
        return Response.builder()
                .id(guide.getId())
                .code(guide.getCode())
                .title(guide.getTitle())
                .content(guide.getContent())
                .status(guide.getStatus())
                .build();
    }
}
