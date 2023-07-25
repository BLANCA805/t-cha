package com.tcha.guide.mapper;

import com.tcha.guide.dto.GuideDto;
import com.tcha.guide.entity.Guide;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuideMapper {
    Guide guidePostDtoToGuide(GuideDto.post requestBody);
    Guide guidePatchDtoToGuide(GuideDto.patch requestBody);
    GuideDto.Response guideToGuideResponse(Guide guide);

}
