package com.tcha.bookmark.controller;

import com.tcha.bookmark.dto.BookmarkDto;
import com.tcha.bookmark.entity.Bookmark;
import com.tcha.bookmark.mapper.BookmarkMapper;
import com.tcha.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/bookmarks")
@Slf4j
@RestController
@RequiredArgsConstructor //생성자 주입, final 변수 주입
@RestControllerAdvice

public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final BookmarkMapper bookmarkMapper;

    //즐겨찾기 등록
    /*
        들어오는 값: userid, trainerid

    */
    @PostMapping
    public ResponseEntity postBookMark(@RequestBody BookmarkDto.post postRequest) {
        Bookmark postBookMark = bookmarkMapper.bookMarkPostDtoToBookMark(postRequest);
        //값을 아래와 같은 코드처럼 입력해야 하는 것인지 아니면, postRequest만 입력해야 하는 것인지 잘 모르겠음 => 확인 필
        Bookmark response = bookmarkService.createBookmark(postBookMark);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    //즐겨찾기 삭제
    @PatchMapping("/{id}")
    public ResponseEntity patchBookMark(@PathVariable("id") Long bookmarkId) {
        int response = bookmarkService.deleteBookmark(bookmarkId);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    //유저별 즐겨찾기 목록보기
    @GetMapping("/{id}")
    public ResponseEntity getBookMark(@PathVariable("id") UUID userId) {
        List<BookmarkDto.Response> response = bookmarkService.findAllUserIdBookMark(userId);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
}
