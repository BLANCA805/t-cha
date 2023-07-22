package com.tcha.notice.enums;

import lombok.Getter;

public enum NoticeEmerStatus {
    NORMAL("공지사항"),
    EMER("긴급 공지사항");

    @Getter
    private String status;

    NoticeEmerStatus(String status) {
        this.status = status;
    }
}