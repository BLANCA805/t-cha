package com.tcha.user_profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserProfileDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        private Long userId;

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
    public static class Response {

        private String name;

        private String profileImage;

    }

}

/*
TODO

- userId type
 */
