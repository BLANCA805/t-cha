package com.tcha.question.mapper;

import com.tcha.question.dto.QuestionDto;
import com.tcha.question.dto.QuestionDto.Response;
import com.tcha.question.entity.Question;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question postToQuestion(QuestionDto.Post postRequest);

    QuestionDto.Response questionToResponse(Question question);

    List<Response> questionsToQuestionList(List<Question> questions);

}
