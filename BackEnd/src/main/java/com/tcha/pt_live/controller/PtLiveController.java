package com.tcha.pt_live.controller;

import com.tcha.pt_live.service.PtLiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lives")
@Validated
@Slf4j
@RequiredArgsConstructor
public class PtLiveController {

    private final PtLiveService ptLiveService;

}
