package com.tcha.answer.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class AnswerDto {

    @AllArgsConstructor
    @Getter
    public static class Post {

        private String content;

    }

    @AllArgsConstructor
    @Getter
    public static class Response {

        private Long id;

        private String content;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

    }

}
