package com.tcha.test.controller;

import com.tcha.test.dto.TestDto;
import com.tcha.test.entity.Test;
import com.tcha.test.mapper.TestMapper;
import com.tcha.test.service.TestService;
import com.tcha.utils.upload.controller.UploadController;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
@Validated
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final TestMapper testMapper;

    @PostMapping()
    public ResponseEntity postTest(
            @RequestBody TestDto.Post postRequest
    ) throws IOException {

        Test testToService = testMapper.postToTest(postRequest);

        Test testForResponse = testService.createTest(
                testToService);
        TestDto.Response response = testMapper.testToResponse(testForResponse);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }
}
