package com.tcha.pt_live.dto;

import com.tcha.pt_live.entity.PtLive.PtliveStaus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PtLiveDto {

    @Getter
    @Builder
    public static class Post {

        @NotBlank
        private String userId;
        @NotBlank
        private long ptClassId;
    }

    @Getter
    @Builder
    public static class Patch {

        @NotBlank
        private String userId; // 프로필 아이디를 받아야 하나?
        @NotBlank
        private long ptLiveId;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        // 라이브 정보
        private long ptLiveId;
        private String status;

        // 클래스 정보
        private long ptClassId;

        // 트레이너 정보
        private String trainerId;
        private String trainerProfileImage;
        private String trainerName;

        // 유저 정보
        private String userId;
        private String userProfileImage;
        private String userName;
    }
}
