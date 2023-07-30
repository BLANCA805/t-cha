package com.tcha.trainer.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class TrainerDto {

    /*
    트레이너 등록 요청
     */
    @Getter
    public static class Post {

        private String introduction;
        private String title;
        private String content;
        private String tags;
        private List<MultipartFile> images;
        private String userProfileId; // 현재 로그인한 유저의 프로필 아이디
    }

    /*
    트레이너 정보 수정 요청
    */
    @Getter
    public static class Patch {

        private String introduction;
        private String title;
        private String content;
        private String tags;
        private List<MultipartFile> images;
    }

    /*
    트레이너 검색 요청
    */
    @Getter
    @Builder
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

        private String id;
        private String introduction;
        private String tags;
        private String title;
        private String content;
        private List<String> images; // 포트폴리오 사진 리스트
        private String profileImg; // 유저 프로필 사진
        private String profileName; // 유저 이름
    }

    /*
    트레이너 목록 정보 응답
    */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseList {

        private String id; // 트레이너 id

        // 카드에 보여지는 트레이너 정보
        private String profileName; // 유저 이름 (트레이너 이름)
        private String profileImg; // 유저 프로필 사진 (트레이너 프사)
        private String introduction; // 트레이너 한 줄 소개
        private String tags; // 트레이너 태그
        private float stars; // 트레이너 별점
        private LocalDateTime createdAt; // 트레이너 등록일
        private int userCount; // 누적 회원 수
        private int ptCount; // 누적 예약 수
        private int reviewCount; // 누적 리뷰 수
        private int revisitGrade; // 재방문율에 따른 등급 (0(일반), 1(브론즈), 2(실버), 3(골드))
    }

}