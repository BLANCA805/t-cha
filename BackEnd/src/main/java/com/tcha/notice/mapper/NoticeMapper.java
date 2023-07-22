package com.tcha.notice.mapper;

import com.tcha.notice.dto.NoticeDto;
import com.tcha.notice.entity.Notice;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface NoticeMapper {

    Notice noticePostDtoToNotice(NoticeDto.Post requestBody);

    Notice noticePatchDtoToNotice(NoticeDto.Patch requestBody);

    NoticeDto.Response noticeResponseDtoToNotice(Notice notice);

    List<NoticeDto.Response> noticesToNoticeResponseDtos(List<Notice> notices);
}
