package com.tcha.question.mapper;

import com.tcha.question.dto.QuestionDto;
import com.tcha.question.dto.QuestionDto.Response;
import com.tcha.question.entity.Question;
import com.tcha.user_profile.entity.UserProfile;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    default Question postToQuestion(QuestionDto.Post postRequest) {

        UserProfile userProfile = new UserProfile();
        userProfile.setId(postRequest.getUserProfileId());

        return Question.builder()
                .userProfile(userProfile)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .status(postRequest.getStatus())
                .build();
    }

    default QuestionDto.Response questionToResponse(Question question) {

        return QuestionDto.Response.builder()
                .questionId(question.getId())
                .userProfileId(question.getUserProfile().getId())
                .title(question.getTitle())
                .content(question.getContent())
                .name(question.getUserProfile().getName())
                .userProfileImage(question.getUserProfile().getProfileImage())
                .status(question.getStatus())
                .createdAt(question.getCreatedAt())
                .modifiedAt(question.getModifiedAt())
                .build();
    }

    List<QuestionDto.Response> questionsToQuestionList(List<Question> questions);

}
