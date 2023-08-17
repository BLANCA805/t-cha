package com.tcha.trainer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class TrainerDto {

    /*
    트레이너 등록 요청
     */
    @Getter
    @Builder
    public static class Post {

        private String introduction;
        private String title;
        private String content;
        private String tags;
        private List<String> images;

    }

    /*
    트레이너 정보 수정 요청
    */
    @Getter
    @Builder
    public static class Patch {

        private String introduction;
        private String title;
        private String content;
        private String tags;
        private List<String> images;

    }

    /*
    트레이너 검색 요청
    */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Get {

        @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-DD", timezone = "Asia/Seoul")
        private LocalDate date;
        @JsonFormat(shape = Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime fromTime;
        private String keyword;
        @JsonFormat(shape = Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime toTime;
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
        private List<Long> userProfileIdList;
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

        // 트레이너 관련 데이터
        private double stars; // 트레이너 평균 별점
        private int reviewCount; // 누적 리뷰 수
        private LocalDate createdAt; // 트레이너 등록일 (yyyy-MM-DD)
        private int ptCount; // 누적 PT 수
        private int revisitGrade; // 재방문율에 따른 등급 (0(일반), 1(브론즈), 2(실버), 3(골드))
        private int bookmarkCount; // 북마크 수
        private List<Long> userProfileIdList;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Rank {

        private String id;
        private double score;

        public static Rank convertToRank(ZSetOperations.TypedTuple<String> Tuple) {
            Rank result = Rank.builder()
                    .id(Tuple.getValue())
                    .score(Tuple.getScore())
                    .build();
            return result;
        }

        @Override
        public String toString() {
            return "Rank{" +
                    "id='" + id + '\'' +
                    ", star=" + score +
                    '}';
        }
    }

}