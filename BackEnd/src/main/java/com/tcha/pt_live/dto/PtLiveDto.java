package com.tcha.pt_live.dto;

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

    // response로 뭘 보내야 할까?
    // -  현재 예약된 pt수업 정보 (예약한 유저에게 보여질 정보)
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        // 라이브 정보
        private long ptLiveId;

        // 클래스 정보
        private long ptClassId;
        private LocalDateTime startAt; // 수업 시작 시간 (날짜 포함)
        private LocalDateTime closeAt; // 수업 종료 시간 (날짜 포함)
        private String price; // 수업 결제 가격

        // 트레이너 정보
        private String trainerId;
        private String trainerProfileImage;
        private String trainerName;

        //라이브 상태 정보
        private String status;
        // 유저 정보
//        private String userId;
//        private String userProfileImage;
//        private String userName;
    }
}
