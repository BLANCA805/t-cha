package com.tcha.review.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


public class ReviewDto {
    @Getter
    @AllArgsConstructor
    public static class Post {

        private String content;

        private float star;

    }
    @AllArgsConstructor
    @Getter
    public static class Response {

        private long id;

        private String content;

        private float star;

        private LocalDateTime created_at;
    }
}
