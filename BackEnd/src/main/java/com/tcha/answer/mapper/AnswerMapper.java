package com.tcha.answer.mapper;

import com.tcha.answer.dto.AnswerDto;
import com.tcha.answer.entity.Answer;
import com.tcha.question.entity.Question;
import com.tcha.user_profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    default Answer postToAnswer(AnswerDto.Post postRequest) {

        Question question = new Question();
        question.setId(postRequest.getQuestionId());

        UserProfile userProfile = new UserProfile();
        userProfile.setId(postRequest.getUserProfileId());

        return Answer.builder()
                .question(question)
                .userProfile(userProfile)
                .content(postRequest.getContent())
                .build();
    }

    default AnswerDto.Response answerToResponse(Answer question) {

        return AnswerDto.Response.builder()
                .answerId(question.getId())
                .questionId(question.getQuestion().getId())
                .userProfileId(question.getUserProfile().getId())
                .name(question.getUserProfile().getName())
                .profileImage(question.getUserProfile().getProfileImage())
                .content(question.getContent())
                .createdAt(question.getCreatedAt())
                .modifiedAt(question.getModifiedAt())
                .build();

    }

}
