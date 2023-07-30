package com.tcha.guide.mapper;

import com.tcha.guide.dto.GuideDto;
import com.tcha.guide.entity.Guide;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuideMapper {

    default Guide guideDtoPostToGuide(GuideDto.Post post) {
        return Guide.builder()
                .title(post.getTitle())
                .code(post.getCode())
                .content(post.getContent())
                .status(Guide.Status.STATUS_ACTIVE)
                .build();
    }

    default GuideDto.Response guideToResponseDto(Guide guide) {
        return GuideDto.Response.builder()
                .id(guide.getId())
                .code(guide.getCode())
                .title(guide.getTitle())
                .content(guide.getContent())
                .status(guide.getStatus())
                .build();
    }
}
