package com.tcha.answer.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class AnswerDto {

    @AllArgsConstructor
    @Getter
    public static class Post {

        private Long questionId;

        private Long userProfileId;

        private String content;

    }

    @AllArgsConstructor
    @Builder
    @Getter
    public static class Response {

        private Long answerId;

        private Long questionId;

        private Long userProfileId;

        private String name;

        private String profileImage;

        private String content;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

    }

}
