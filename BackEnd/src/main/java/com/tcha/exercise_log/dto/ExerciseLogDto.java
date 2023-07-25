package com.tcha.exercise_log.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

public class ExerciseLogDto {
    @Getter
    @AllArgsConstructor
    public static class Post {

        private String title;

        private String content;

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private Long id;

        private String title;

        private String content;

        public ExerciseLogDto.Patch addExerciseLogId(Long id) {
            Assert.notNull(id, "id must not be null");
            this.id = id;
            return this;
        }

    }


    @AllArgsConstructor
    @Getter
    public static class Response {

        private long id;

        private String title;

        private String content;

    }
}
