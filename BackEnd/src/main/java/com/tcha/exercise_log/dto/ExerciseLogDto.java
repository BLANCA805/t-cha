package com.tcha.exercise_log.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

public class ExerciseLogDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private String title;

        private String content;

        private String[] images;

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private String title;

        private String content;

        private String[] images;

    }


    @AllArgsConstructor
    @Getter
    public static class Response {

        private long id;

        private String title;

        private String content;

        private String[] images;

    }
}
