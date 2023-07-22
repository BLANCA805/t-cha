package com.tcha.notice.dto;

import com.tcha.notice.enums.NoticeEmerStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;


public class NoticeDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

//        private long id;

        private NoticeEmerStatus status;

        private String title;

        private String content;

        private LocalDateTime created_at;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {

        private long id;

        private NoticeEmerStatus status;

        private String title;

        private String content;

        private LocalDateTime created_at;

        public Patch addNoticeId(Long id) {
            Assert.notNull(id, "id must not be null");
            this.id = id;
            return this;
        }

    }


    @AllArgsConstructor
    @Getter
    public static class Response {

        private long id;

        private NoticeEmerStatus status;

        private String title;

        private String content;

        private LocalDateTime created_at;
    }
}
