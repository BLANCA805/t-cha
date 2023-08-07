package com.tcha.notice.dto;

import com.tcha.notice.entity.Notice.NoticeEmerStatus;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;


public class NoticeDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Post {

        @NotNull
        private NoticeEmerStatus status;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Patch {
        @NotNull
        private NoticeEmerStatus status;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Get {

        @NotNull
        private long id;

        @NotNull
        private NoticeEmerStatus status;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {

        @NotNull
        private long id;

        @NotNull
        private NoticeEmerStatus status;

        @NotBlank
        private String title;

        @NotBlank
        private String content;

    }
}
