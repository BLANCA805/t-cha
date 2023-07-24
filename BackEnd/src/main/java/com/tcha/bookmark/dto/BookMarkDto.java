package com.tcha.bookmark.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class BookMarkDto {

    @Getter
    public static class post {
        @NotBlank
        private Long trainerId;
        private String userId;

    }

    @Getter
    public static class patch {
        @NotBlank
        private Long trainerId;
        @NotBlank
        private String userId;
        private String status;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        @NotBlank
        private Long trainerID;
        private String status;
    }

}
