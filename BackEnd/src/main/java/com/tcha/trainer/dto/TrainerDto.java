package com.tcha.trainer.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

public class TrainerDto {

    /*
    트레이너 정보 수정 요청
    */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestPatch {

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
    public static class RequestSearch {

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
    public static class ResponseInfo {

        private String introduction;
        private String tags;
        private String title;
        private String content;
        private List<String> images; // 포트폴리오 사진

        private String profileImg; // 유저 프로필 사진
        private String profileName; // 유저 이름
    }

    /*
    트레이너 목록에 보여지는 데이터
    트레이너 프로필 사진(유저), 아이디, 이름(유저)
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DtoForResponseList {

        private String profileName; // 유저 이름
        private String profileImg; // 유저 프로필 사진
        private UUID id; // 트레이너 아이디
    }

    /*
    트레이너 검색 응답
    트레이너 사진, 아이디, 이름만 전달 (추후 트레이너 객체 자체 전달로 수정 가능성 있음)
    */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseList {

        private List<DtoForResponseList> trainerList; // 트레이너 검색 결과 리스트
    }

}