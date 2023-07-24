package com.tcha.bookmark.controller;

import com.tcha.bookmark.Mapper.BookMarkMapper;
import com.tcha.bookmark.dto.BookMarkDto;
import com.tcha.bookmark.entity.BookMark;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmarks")
@Slf4j
@RequiredArgsConstructor
public class BookMarkController {
    
    private final BookMarkMapper bookMarkMapper;
//    private final BookMarkService bookMarkService;


    //즐겨찾기 등록
    @PostMapping
    public ResponseEntity postBookMark(@RequestBody BookMarkDto.post postRequest) {

    }

    //즐겨찾기 해제
    @PatchMapping
    public ResponseEntity patchBookMark(@RequestBody BookMarkDto.patch patchRequest) {

    }

    //유저별 즐겨찾기 목록보기
    @GetMapping("/{id}")
    public ResponseEntity getBookMark(@PathVariable String userId, @RequestBody BookMarkDto.post postRequest) {

    }
}
