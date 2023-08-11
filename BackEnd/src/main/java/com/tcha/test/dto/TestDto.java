package com.tcha.test.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class TestDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Post {

        private String title;

        private String content;

        private Double star;

//        private List<String> images;

    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Patch {

        private String title;

        private String content;

    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {

        private long id;

        private String title;

        private String content;

        private Double star;

//        private List<String> images;

    }
}
