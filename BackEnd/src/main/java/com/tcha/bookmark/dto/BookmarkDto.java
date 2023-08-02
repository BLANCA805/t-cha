package com.tcha.bookmark.dto;

import com.tcha.trainer.entity.Trainer;
import com.tcha.user.entity.User;
import com.tcha.user_profile.entity.UserProfile;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;
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
