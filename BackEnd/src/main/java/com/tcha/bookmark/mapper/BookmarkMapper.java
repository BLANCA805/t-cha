package com.tcha.bookmark.mapper;

import com.tcha.bookmark.dto.BookmarkDto;
import com.tcha.bookmark.entity.Bookmark;
import com.tcha.trainer.entity.Trainer;
import com.tcha.user_profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    //post 등록을 위한 entity객체 생성
    default Bookmark postToBookmark(UserProfile userProfile, Trainer trainer) {
        return Bookmark.builder().userProfile(userProfile).trainer(trainer).build();
    }

    //bookMark Entity를 bookMarkDto로 변환
    default BookmarkDto.Response bookMarkToBookMarkDto(Bookmark bookMark) {
        return BookmarkDto.Response.builder().id(bookMark.getId()).trainer(bookMark.getTrainer())
                .userProfile(bookMark.getUserProfile()).build();
    }
}
