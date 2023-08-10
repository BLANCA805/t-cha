package com.tcha.user.dto;

import com.tcha.user.entity.User.UserStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDto {

    @AllArgsConstructor
    @Getter
    public static class Response {

        private String id;

        private String email;

        private UserStatus status;

        private List<String> roles;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LoginResponseDto {

        private String userId;
        private Long userProfileId;
        private String trainerId;
        private String name;
        private String userProfileImage;

    }
}

/*
TODO

- Dto 자체에 필요성 판단
 */
