package com.tcha.user_profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserProfileDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Post {

        @NotBlank
        private String userId;

        @NotBlank
        private String name;

        private String profileImage;

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private String name;

        private String profileImage;

    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {

        private Long id;

        private String name;

        private String profileImage;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

    }

}

/*
TODO

- userId type
 */
