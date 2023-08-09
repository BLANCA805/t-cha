package com.tcha.bookmark.mapper;

import com.tcha.bookmark.dto.BookmarkDto;
import com.tcha.bookmark.entity.Bookmark;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    List<BookmarkDto.Response> bookmarkListToBookmarkDtoResponse(List<Bookmark> bookmarks);


    //bookMark Entity를 bookMarkDto로 변환
    default BookmarkDto.Response bookMarkToBookMarkDtoResponse(Bookmark bookMark) {
        return BookmarkDto.Response.builder()
                .id(bookMark.getId())
                .trainerName(bookMark.getTrainer().getUserProfile().getName())
                .trainerId(bookMark.getTrainer().getId())
                .userProfileName(bookMark.getUserProfile().getName())
                .build();
    }
}
