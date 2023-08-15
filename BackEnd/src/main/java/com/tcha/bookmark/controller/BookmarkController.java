package com.tcha.bookmark.controller;

import com.tcha.bookmark.dto.BookmarkDto;
import com.tcha.bookmark.service.BookmarkService;
import com.tcha.utils.pagination.MultiResponseDto;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/bookmarks")
@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class BookmarkController {

    private final BookmarkService bookmarkService;

    /* 등록 가능한 상태인 트레이너만 즐겨찾기 가능하도록 로직이 구현되어 있을 것이므로 따로, 인증할 필요 없음
        => 로그인된 유저인지만 확인되면 ok
    */
    @PostMapping("{user-profile-id}/{trainer-id}")
    public ResponseEntity<BookmarkDto.Response> postBookmark(
            @PathVariable("user-profile-id") @Positive long userProfileId,
            @PathVariable("trainer-id") String trainerId) {
        BookmarkDto.Response response = bookmarkService.createBookmark(userProfileId, trainerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //아이디를 가지고 즐겨찾기 삭제
    @DeleteMapping("/{bookmark-id}")
    public ResponseEntity deleteBookmark(
            @PathVariable("bookmark-id") @Positive Long bookmarkId) {
        bookmarkService.deleteBookmark(bookmarkId);
        return ResponseEntity.noContent().build();
    }

    //트레이너 아이디와 유저 아이디를 가지고 즐겨찾기 삭제
    @DeleteMapping("/{user-profile-id}/{trainer-id}")
    public ResponseEntity deleteBookmarkByUserProfileIdAndTrainerId(
            @PathVariable("user-profile-id") @Positive Long userProfileID,
            @PathVariable("trainer-id") String trainerId) {
        bookmarkService.deleteBookmarkByUserProfileIdAndTrainerId(userProfileID, trainerId);
        return ResponseEntity.noContent().build();
    }

    //유저별 즐겨찾기 목록보기
    @GetMapping("/{user-profile-id}")
    public ResponseEntity<MultiResponseDto<BookmarkDto.Response>> getBookmark(
            @RequestParam(value = "page", defaultValue = "1") @Positive Integer page,
            @RequestParam(value = "size", defaultValue = "10") @Positive Integer size,
            @PathVariable("user-profile-id") @Positive Long userProfileId) {
        MultiResponseDto<BookmarkDto.Response> response = bookmarkService.findAllUserIdBookMark(
                page, size, userProfileId);
        return ResponseEntity.ok().body(response);
    }

    //트레이너 상세조회 페이지에서 즐겨찾기 등록 여부 확인
    @GetMapping("/{user-profile-id}/{trainer-id}")
    public ResponseEntity<BookmarkDto.Response> getFindBookmarkIdByUserProfileIdAndTrainerId(
            @PathVariable("user-profile-id") @Positive Long userProfileID,
            @PathVariable("trainer-id") String trainerId) {

        BookmarkDto.Response response = bookmarkService.getFindBookmarkIdByUserProfileIdAndTrainerId(
                userProfileID, trainerId);

        return ResponseEntity.ok().body(response);
    }
}
