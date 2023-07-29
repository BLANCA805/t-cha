package com.tcha.user_profile.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserProfileDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private String name;

        private String profileImage;

    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private String profileImage;

    }

    @Getter
    @AllArgsConstructor
    public static class Response {

        private String id;

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
