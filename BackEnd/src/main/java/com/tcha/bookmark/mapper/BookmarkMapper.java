package com.tcha.bookmark.mapper;

import com.tcha.bookmark.dto.BookmarkDto;
import com.tcha.bookmark.entity.Bookmark;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    //post등록을 위한 dto객체를 entity객체로 변환
    Bookmark bookMarkPostToBookmark(BookmarkDto.Post bookMarkPost);

    //bookMark Entity를 bookMarkDto로 변환
    BookmarkDto.Response bookMarkToBookMarkDto(Bookmark bookMark);
}
