package com.tcha.guide.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guide")
@Slf4j
@RequiredArgsConstructor //생성자 주입
public class GuideController {

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
    public ResponseEntity postGuide(){

    }

}
