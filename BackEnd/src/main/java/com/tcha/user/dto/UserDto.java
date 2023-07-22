package com.tcha.user.dto;

import com.tcha.user.entity.User.UserStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDto {

    @AllArgsConstructor
    @Getter
    public static class Post{

    }

    @AllArgsConstructor
    @Getter
    public static class Response {

        private String id;

        private String email;

        private UserStatus status;

        private List<String> roles;

        private LocalDateTime createAt;

        private LocalDateTime modifiedAt;

    }
}

/*
TODO

- Dto 자체에 필요성 판단
 */
