//package com.tcha.bookmark.controller;
//
//import com.tcha.bookmark.dto.BookMarkDto.Response;
//import com.tcha.bookmark.mapper.BookMarkMapper;
//import com.tcha.bookmark.dto.BookMarkDto;
//import com.tcha.bookmark.entity.BookMark;
//import com.tcha.bookmark.service.BookMarkService;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RequestMapping("/bookmarks")
//@Slf4j
//@RestController
//@RequiredArgsConstructor //생성자 주입, final 변수 주입
//@RestControllerAdvice
//
//public class BookMarkController {
//
//    private final BookMarkService bookMarkService;
//    private final BookMarkMapper bookMarkMapper;
//
//    //즐겨찾기 등록
//    /*
//        들어오는 값: userid, trainerid
//
//    */
//    @PostMapping
//    public ResponseEntity postBookMark(@RequestBody BookMarkDto.post postRequest) {
//        BookMark postBookMark = bookMarkMapper.bookMarkPostDtoToBookMark(postRequest);
//        //값을 아래와 같은 코드처럼 입력해야 하는 것인지 아니면, postRequest만 입력해야 하는 것인지 잘 모르겠음 => 확인 필
//        BookMark response = bookMarkService.registBookMark(postBookMark, postRequest.getTrainerId(),
//                postRequest.getUserId());
//        return new ResponseEntity(response, HttpStatus.CREATED);
//    }
//
//    //즐겨찾기 해제
//    @PatchMapping
//    public ResponseEntity patchBookMark(@RequestBody BookMarkDto.patch patchRequest) {
//        BookMark patchBookMark = bookMarkMapper.bookMarkPatchDtoToBookMark(patchRequest);
//        int response = bookMarkService.patchBookMark(patchBookMark);
//        return new ResponseEntity(response, HttpStatus.CREATED);
//    }
//
//    //유저별 즐겨찾기 목록보기
//    @GetMapping("/{id}")
//    public ResponseEntity getBookMark(@PathVariable("id") String userId) {
//        List<BookMarkDto.Response> response = bookMarkService.
//    }
//}
