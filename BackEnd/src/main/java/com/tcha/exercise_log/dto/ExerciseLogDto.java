package com.tcha.exercise_log.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

public class ExerciseLogDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Post {

        private String title;

        private String content;

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

        private List<String> images;

        private List<String> videos;

    }
}
