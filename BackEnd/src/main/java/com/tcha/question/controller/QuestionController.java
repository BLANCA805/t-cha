package com.tcha.question.controller;

import com.tcha.question.dto.QuestionDto;
import com.tcha.question.entity.Question;
import com.tcha.question.mapper.QuestionMapper;
import com.tcha.question.servicce.QuestionService;
import com.tcha.utils.pagination.MultiResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
@Validated
@Slf4j
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionMapper questionMapper;
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity postQuestion(@RequestBody QuestionDto.Post postRequest) {

        Question questionForService = questionMapper.postToQuestion(postRequest);
        Question questionForResponse = questionService.createQuestion(questionForService);
        QuestionDto.Response response = questionMapper.questionToResponse(questionForResponse);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("/{question-id}")
    public ResponseEntity getOneQuestion(@PathVariable("question-id") Long questionId) {

        Question questionForResponse = questionService.findOneQuestion(questionId);
        QuestionDto.Response response = questionMapper.questionToResponse(questionForResponse);

        return new ResponseEntity(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity getAllQuestions(@RequestParam int page, @RequestParam int size) {

        Page<Question> pageQuestion = questionService.findAllQuestions(page - 1, size);
        List<Question> memberListForResponse = pageQuestion.getContent();
        List<QuestionDto.Response> response = questionMapper.questionsToQuestionList(
                memberListForResponse);

        return new ResponseEntity(new MultiResponseDto<>(response, pageQuestion), HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable("question-id") Long questionId) {

        questionService.deleteQuestion(questionId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
