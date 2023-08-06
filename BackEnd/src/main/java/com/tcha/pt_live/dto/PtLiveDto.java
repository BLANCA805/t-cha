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
    public class Post {

        @NotBlank
        private long userProfileId;
        @NotBlank
        private long ptClassId;
    }

    // response로 뭘 보내야 할까?
    // -  현재 예약된 pt수업 정보
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private long ptLiveId; // 라이브 아이디
        private String trainerProfileImage; // 트레이너 프로필 이미지 url
        private String trainerName; // 트레이너 이름
        private LocalDateTime startAt; // pt 수업 시작 날짜 및 시간
        private LocalDateTime closeAt; // pt 수업 종료 날짜 및 시간 (시작 시간 +1시간)
        private String price; // 수업 결제 가격
    }
}
