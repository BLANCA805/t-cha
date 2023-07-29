package com.tcha.bookmark.dto;

import com.tcha.trainer.entity.Trainer;
import com.tcha.user.entity.User;
import com.tcha.user_profile.entity.UserProfile;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BookmarkDto {


    @Builder
    @AllArgsConstructor
    public static class Post {

        @NotBlank
        private Trainer trainer;
        @NotBlank
        private UserProfile userProfile;
    }

    @Builder
    @AllArgsConstructor
    public static class Response {

        @NotBlank
        private UserProfile userProfile;
        @NotBlank
        private Trainer trainer;
        private Long id;
    }

}
