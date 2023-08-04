package com.tcha.test.controller;

import com.tcha.test.dto.TestDto;
import com.tcha.test.entity.Test;
import com.tcha.test.mapper.TestMapper;
import com.tcha.test.service.TestService;
import com.tcha.utils.pagination.MultiResponseDto;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping()
    public ResponseEntity getTest(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        Page<Test> testPage = testService.findTestPages(page, size);
        List<Test> tests = testPage.getContent();
        List<TestDto.Response> responses = testMapper.testsToResponses(
                tests);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, testPage),
                HttpStatus.OK);
    }
}
