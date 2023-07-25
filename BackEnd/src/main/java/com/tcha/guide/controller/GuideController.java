package com.tcha.guide.controller;

import com.tcha.guide.dto.GuideDto;
import com.tcha.guide.entity.Guide;
import com.tcha.guide.mapper.GuideMapper;
import com.tcha.guide.service.GuideService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guides")
@Slf4j
@RequiredArgsConstructor //생성자 주입, final 변수 주입
@RestControllerAdvice
public class GuideController {

    private final GuideService guideService;
    private final GuideMapper guideMapper;


    //아직 기능 코드에 대한 조건 없음, 추후 유효성 검사 로직 추가 예정
    @PostMapping
    public ResponseEntity postGuide(@RequestBody GuideDto.post postRequest) {
        Guide postGuide = guideMapper.guidePostDtoToGuide(postRequest);
        Guide guideForResponse = guideService.createGuide(postGuide);
        GuideDto.Response response = guideMapper.guideToGuideResponse(guideForResponse);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    //기능 코드별 사용 가이드 보기
    @GetMapping("/{code}")
    public ResponseEntity getGuide(@PathVariable("code") String code) throws Exception {
        List<GuideDto.Response> response = guideService.findAllCodeGuide(code);
        return new ResponseEntity(response, HttpStatus.CREATED);

    }

    //1개의 사용 가이드 확인
    @GetMapping("/code/{id}")
    public ResponseEntity getOneGuide(@PathVariable("id") Long id) throws Exception {
        GuideDto.Response response = guideService.findOneGuide(id);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    //서비스 가이드 수정
    @PatchMapping
    public ResponseEntity patchGuide(@RequestBody GuideDto.patch patchRequest) {
        Guide patchGuide = guideMapper.guidePatchDtoToGuide(patchRequest);
        int response = guideService.patchGuide(patchGuide);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }


}
