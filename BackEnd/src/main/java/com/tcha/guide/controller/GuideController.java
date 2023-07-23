package com.tcha.guide.controller;

import com.tcha.guide.dto.GuideDto;
import com.tcha.guide.entity.Guide;
import com.tcha.guide.mapper.GuideMapper;
import com.tcha.guide.service.GuideService;
import com.tcha.user.dto.UserDto;
import com.tcha.user.entity.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guide")
@Slf4j
@RequiredArgsConstructor //생성자 주입
public class GuideController {

    private final GuideService guideService;
    private final GuideMapper guideMapper;

/*
    postGuide
            patchGuide
    getGuide
            deleteGuide
    patchDeregistGuide
    */
//    private final GuideServie guideServie;


    //아직 기능 코드에 대한 조건이 없으므로 유효성 검사 필요 없으나 추후에 추가해야 될 수도 있음
    @PostMapping
    public ResponseEntity postGuide(@RequestBody GuideDto.post postRequest){
        Guide postGuide = guideMapper.guidePostDtoToGuide(postRequest);
        Guide guideForResponse = guideService.createGuide(postGuide);
        GuideDto.Response response = guideMapper.guideToGuideResponse(guideForResponse);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    //1개의 사용 가이드 확인
//    @GetMapping("/code/{id}")
//    public ResponseEntity getOndGuide(@PathVariable("code") String code, @PathVariable("id") Long id){
//
//        Guide getGuide = guideService.findGuide(id);
//        GuideDto.Response response = guideMapper.guideToGuideResponse(getGuide);
//        return new ResponseEntity(response, HttpStatus.CREATED);
//
//    }

    //여러개의 사용 가이드 확인

}
