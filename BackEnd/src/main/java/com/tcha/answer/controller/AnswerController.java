package com.tcha.answer.controller;

import com.tcha.answer.dto.AnswerDto;
import com.tcha.answer.entity.Answer;
import com.tcha.answer.mapper.AnswerMapper;
import com.tcha.answer.servicce.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answers")
@Validated
@Slf4j
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerMapper answerMapper;
    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity postAnswer(@RequestBody AnswerDto.Post postRequest) {

        Answer answerForService = answerMapper.postToAnswer(postRequest);
        Answer answerForResponse = answerService.createAnswer(answerForService);
        AnswerDto.Response response = answerMapper.answerToResponse(answerForResponse);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("/{answer-id}")
    public ResponseEntity getOneAnswer(@PathVariable("answer-id") Long answerId) {

        Answer answerForResponse = answerService.findOneAnswer(answerId);
        AnswerDto.Response response = answerMapper.answerToResponse(answerForResponse);

        return new ResponseEntity(response, HttpStatus.OK);

    }

    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") Long answerId) {

        answerService.deleteAnswer(answerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
