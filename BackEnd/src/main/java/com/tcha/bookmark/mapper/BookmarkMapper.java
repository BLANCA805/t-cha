package com.tcha.bookmark.mapper;

import com.tcha.bookmark.dto.BookmarkDto;
import com.tcha.bookmark.entity.Bookmark;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    Bookmark bookMarkPostDtoToBookMark(BookmarkDto.post requestBody);
    
    BookmarkDto.Response bookMarkToBookMarkResponse(Bookmark bookMark);
}
