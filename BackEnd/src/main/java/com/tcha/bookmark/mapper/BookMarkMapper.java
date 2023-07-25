package com.tcha.bookmark.mapper;

import com.tcha.bookmark.dto.BookMarkDto;
import com.tcha.bookmark.entity.BookMark;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMarkMapper {

    BookMark bookMarkPostDtoToBookMark(BookMarkDto.post requestBody);

    BookMark bookMarkPatchDtoToBookMark(BookMarkDto.patch requestBody);

    BookMarkDto.Response bookMarkToBookMarkResponse(BookMark bookMark);
}
