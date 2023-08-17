package com.tcha.pt_class.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.tcha.exercise_log.entity.ExerciseLog;
import com.tcha.pt_live.entity.PtLive.PtliveStatus;
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

        //ptlive에 대한 상태값: 종류: INACCESSIBLE, ACCESSIBLE, TERMINABLE, TERMINATION
        // INACCESSIBLE: "접근 불가" 상태로 pt시작 5분전에 진행중으로 변경
        // ACCESSIBLE: "진행중" 상태로 pt방에 접근 가능한 상태
        // TERMINABLE: "종료가능"상태 등록된 ptlive가 끝난 이후 10분까지 종료가능 상태 유지
        // TERMINATION: "종료"된 상태
        // -> 오픈비두 접근이랑, 리뷰 작성때 사용하면 될듯
        private PtliveStatus ptLiveStatus;

        //운동일지에 대한 싱태값: READ, WRITE
        // READ: "읽기"만 되는 상태, 유저 & 트레이너 접근 가능
        // WRITE: "쓰기"만 되는 상태, 트레이너만 접근 가능

        private ExerciseLog.exerciseLogStatus exerciseLogStatus;
        private Long reviewId;
        private String trainerName;
        private String trainerImage;
        private String userName;
        private String userImage;
        private String introduction;

    }
}
