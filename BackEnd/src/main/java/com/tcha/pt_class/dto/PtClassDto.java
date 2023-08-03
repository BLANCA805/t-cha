package com.tcha.pt_class.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
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
        private Long classId;
        private LocalDateTime startAt; // 변경할 시간
    }

    @Getter
    @Builder
    public static class Response {

        // 현재 트레이너의 전체 수업 목록
        private long classId;
        private LocalDateTime startAt;
    }
}
