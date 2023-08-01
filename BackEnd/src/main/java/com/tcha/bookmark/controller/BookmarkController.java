package com.tcha.bookmark.controller;

import com.tcha.bookmark.dto.BookmarkDto;
import com.tcha.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bookmarks")
@Slf4j
@RestController
@RequiredArgsConstructor
@RestControllerAdvice

public class BookmarkController {

    private final BookmarkService bookmarkService;

    /* 등록 가능한 상태인 트레이너만 즐겨찾기 가능하도록 로직이 구현되어 있을 것이므로 따로, 인증할 필요 없음
        => 로그인된 유저인지만 확인되면 ok
    */
    @PostMapping("{user-profile-id}/{trainer-id}")
    public ResponseEntity<BookmarkDto.Response> postBookMark(
            @PathVariable("user-profile-id") Long userProfileId,
            @PathVariable("trainer-id") String trainerId) {
        System.out.println("data test >>>>>>>>>> " + userProfileId + " " + trainerId);
        BookmarkDto.Response response = bookmarkService.createBookmark(userProfileId, trainerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //즐겨찾기 삭제
    @DeleteMapping("/{bookmark-id}")
    public ResponseEntity patchBookMark(
            @PathVariable("bookmark-id") Long bookmarkId) {
        bookmarkService.deleteBookmark(bookmarkId);
        return ResponseEntity.noContent().build();
    }

    //유저별 즐겨찾기 목록보기
    @GetMapping("/{user-profile-id}")
    public ResponseEntity<List<BookmarkDto.Response>> getBookMark(
            @PathVariable("user-profile-id") Long userProfileId) {
        List<BookmarkDto.Response> response = bookmarkService.findAllUserIdBookMark(userProfileId);
        return ResponseEntity.ok().body(response);
    }
}
