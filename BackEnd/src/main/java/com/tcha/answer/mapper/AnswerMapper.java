package com.tcha.answer.mapper;

import com.tcha.answer.dto.AnswerDto;
import com.tcha.answer.entity.Answer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    Answer postToAnswer(AnswerDto.Post postRequest);

    AnswerDto.Response answerToResponse(Answer question);

}
