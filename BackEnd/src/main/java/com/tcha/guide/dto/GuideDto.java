package com.tcha.guide.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.tcha.guide.entity.Guide.Status;

public class GuideDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Post {

        @NotNull
        private String code;
        @NotBlank
        private String title;

        private String content;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Patch {

        @NotBlank
        private String code;
        @NotBlank
        private String title;

        private String content;

        private Status status;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String code;
        private String content;
        private Status status;
    }

}
