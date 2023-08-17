package com.tcha.exercise_log.dto;


import java.util.List;

import com.tcha.exercise_log.entity.ExerciseLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

public class ExerciseLogDto {

//    @Getter
//    @AllArgsConstructor
//    @Builder
//    public static class Post {
//
//        private String title;
//
//        private String content;
//
////        private List<String> images;
////
////        private List<String> videos;
//
//    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Content {
        private String text;
        private String image;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Patch {

        private String title;

        private List<Content> contents;

//        private List<String> images;
//
//        private List<String> videos;

    }


    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {

        private long id;

        private String title;

        private List<String> contents;

        private List<String> images;
        //
        private List<String> videos;

        private String profileName;

        private String trainerName;


        private ExerciseLog.exerciseLogStatus status;
    }
}
