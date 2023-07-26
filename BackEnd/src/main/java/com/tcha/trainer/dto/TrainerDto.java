package com.tcha.trainer.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

public class TrainerDto {

    /*
    트레이너 등록 요청
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        private String introduction;
        private String title;
        private String content;
        private String tags;
        private String userProfileId; // 현재 로그인한 유저의 프로필 아이디
    }

    /*
    트레이너 정보 수정 요청
    */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {

        private String introduction;
        private String title;
        private String content;
        private String tags; // *고민* 태그 수정을 전체 정보 수정과 분리?
    }

    /*
    트레이너 검색 요청
    */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Get {

        private String keyword;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime datetime; // *고민* 날짜와 시간을 분리?
    }

    /*
    트레이너 상세 정보 응답
    */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private String introduction;
        private String tags;
        private String title;
        private String content;
        private List<String> images; // 포트폴리오 사진
        private String profileImg; // 유저 프로필 사진
        private String profileName; // 유저 이름
    }

}