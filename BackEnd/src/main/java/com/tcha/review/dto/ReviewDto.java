package com.tcha.review.dto;

import com.tcha.user_profile.entity.UserProfile;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ReviewDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Post {

        private Long userId ;
        private String trainerId;
        private Long ptLiveId;
        private String content;

        private float star;

    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Get {


    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response {

        private long id;

        private String content;

        private float star;

        private Long ptClassId;

        private LocalDate startDate;

        private String profileImg; // 유저 프로필 사진

        private String profileName; // 유저 이름

        private String trainerProfileImg; // 트레이너 프로필 사진

        private String trainerProfileName; // 트레이너 이름
    }
}
