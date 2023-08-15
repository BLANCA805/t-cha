package com.tcha.bookmark.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class BookmarkDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class Response {

        @NotBlank
        private String userProfileName;
        @NotBlank
        private String trainerName;
        @NotBlank
        private String trainerId;
        private Long id;
    }

}
