package com.tcha.bookmark.dto;

import com.tcha.trainer.entity.Trainer;
import com.tcha.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BookmarkDto {


    public static class post {
        @NotBlank
        private UUID trainerId;
        @NotBlank
        private UUID userId;
    }

    @Builder
    @AllArgsConstructor
    public static class Response {
        @NotBlank
        private User user;
        @NotBlank
        private Trainer trainer;
        private Long id;
    }

}
