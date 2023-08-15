package com.tcha.notice.dto;

import com.tcha.notice.entity.Notice.NoticeEmerStatus;
import java.time.LocalDateTime;
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

        private NoticeEmerStatus status;

        private String title;

        private String content;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Patch {

        private NoticeEmerStatus status;

        private String title;

        private String content;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Get {

        private long id;

        private NoticeEmerStatus status;

        private String title;

        private String content;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {

        private long id;

        private NoticeEmerStatus status;

        private String title;

        private String content;

    }
}
