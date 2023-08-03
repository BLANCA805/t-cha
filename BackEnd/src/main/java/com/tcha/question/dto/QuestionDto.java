package com.tcha.question.dto;

import com.tcha.question.entity.Question.QuestionStatus;
import com.tcha.user_profile.entity.UserProfile;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class QuestionDto {

    @AllArgsConstructor
    @Getter
    public static class Post {

        private Long userProfileId;

        private String title;

        private String content;

        private QuestionStatus status;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {

        private Long questionId;

        private Long userProfileId;

        private String title;

        private String content;

        private String name;

        private String userProfileImage;

        private QuestionStatus status;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

    }

}
