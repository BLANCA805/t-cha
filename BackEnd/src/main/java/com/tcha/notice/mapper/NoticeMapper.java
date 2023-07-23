package com.tcha.notice.mapper;

import com.tcha.notice.dto.NoticeDto;
import com.tcha.notice.entity.Notice;
import java.util.List;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface NoticeMapper {

    Notice postToNotice(NoticeDto.Post postRequest);

    Notice patchToNotice(NoticeDto.Patch patchRequest);

    NoticeDto.Response noticeToResponse(Notice notice);

    List<NoticeDto.Response> noticesToResponses(List<Notice> notices);
}
