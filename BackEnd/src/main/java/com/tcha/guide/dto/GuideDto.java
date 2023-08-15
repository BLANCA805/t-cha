package com.tcha.guide.dto;

import jakarta.validation.constraints.NotBlank;
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

        @NotBlank
        private String code;
        private String title;

        private String content;

    }

    @Getter
    @Builder
    @NotBlank
    @AllArgsConstructor
    public static class Patch {
        @NotBlank
        private Long id;
        @NotBlank
        private String code;

        private String content;

        private Status status;
        private String title;
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
