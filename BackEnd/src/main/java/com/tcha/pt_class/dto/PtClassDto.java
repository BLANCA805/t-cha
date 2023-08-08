package com.tcha.pt_class.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class PtClassDto {

    @Getter
    @Builder
    public static class Post {

        @NotBlank
        private String trainerId; // 수업을 오픈하는 트레이너, 필수
        private LocalDate date; // 수업을 등록하는 날짜
        @JsonFormat(shape = Shape.STRING, pattern = "HH:mm")
        private List<LocalTime> startTimeList; // pt 수업 시작 시간 목록
    }

    @Getter
    @Builder
    public static class Patch {

        @NotBlank
        private String userId; // 예약을 신청/취소하는 유저
        @NotNull
        private Long ptClassId; // 예약/취소하려는 수업
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Get {

        @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-DD", timezone = "Asia/Seoul")
        private LocalDate date;
        @JsonFormat(shape = Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        @Builder.Default
        private LocalTime fromTime = LocalTime.parse("00:00");
        @JsonFormat(shape = Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        @Builder.Default
        private LocalTime toTime = LocalTime.parse("23:59");
    }

    @Getter
    @Builder
    public static class Response {

        private String trainerId;
        private Long classId;
        private Long liveId;
        private LocalDate startDate;
        private LocalTime startTime;
    }
}
