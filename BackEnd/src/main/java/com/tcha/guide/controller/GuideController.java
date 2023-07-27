package com.tcha.guide.controller;

import com.tcha.guide.dto.GuideDto.Post;
import com.tcha.guide.dto.GuideDto.Patch;
import com.tcha.guide.dto.GuideDto.Response;
import com.tcha.guide.service.GuideService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guides")
@Slf4j
@RequiredArgsConstructor //생성자 주입, final 변수 주입
//@RestControllerAdvice
public class GuideController {

    private final GuideService guideService;

    //서비스 가이드 등록
    @PostMapping
    public ResponseEntity postGuide(@RequestBody Post postRequest) {
        Response response = guideService.createGuide(postRequest);
        return ResponseEntity.ok().body(response);
    }

    //기능 코드별 사용 가이드 보기
    @GetMapping("/{code}")
    public ResponseEntity getGuide(@PathVariable("code") String code) {
        List<Response> response = guideService.findAllCodeGuide(code);
        return ResponseEntity.ok().body(response);

    }

    //1개의 사용 가이드 확인
    @GetMapping("/code/{id}")
    public ResponseEntity getOneGuide(@PathVariable("id") Long id) {
        Response response = guideService.findOneGuide(id);

        return ResponseEntity.ok().body(response);
    }

    //서비스 가이드 수정
    @PatchMapping("{id}")
    public ResponseEntity patchGuide(@PathVariable("id") Long id, @RequestBody Patch patchRequest) {
        Response response = guideService.patchGuide(id, patchRequest);
        return ResponseEntity.ok().body(response);
    }

    //서비스 가이드 삭제
    @DeleteMapping("{id}")
    public ResponseEntity deleteGuide(@PathVariable("id") Long id) throws Exception {
        guideService.deleteGuide(id);
        return ResponseEntity.noContent().build();
    }

}
