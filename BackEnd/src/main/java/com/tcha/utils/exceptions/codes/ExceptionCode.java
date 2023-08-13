package com.tcha.utils.exceptions.codes;

import com.tcha.exercise_log.dto.ExerciseLogDto;
import lombok.Getter;

public enum ExceptionCode {
    USER_NOT_FOUND(404, "User not found"),
    USER_PROFILE_NOT_FOUND(404, "User Profile not found"),
    QUESTION_NOT_FOUND(404, "Question not found"),

    //에러 처리 확인을 위해 추가(08.06 06:55)
    TRAINER_NOT_FOUND(404, "Trainer not found"),
    BOOKMARK_NOT_FOUND(404, "Bookmark not found"),

    //에러 처리 확인을 위해 추가(08.07 02:22)
    GUIDE_NOT_FOUND(404, "Guide not found"),
    GUIDE_EXISTS(409, "Guide exists"),
    NOTICE_NOT_FOUND(404, "Notice not found"),

    EXERCISELOG_EXISTS(409, "Exerciselog exists"),
    EXERCISELOG_NOT_FOUND(404, "Exerciselog not found"),
    //    NOTICE_EXISTS(409, "Notice exists");
    // 추가 (08.08 17:17, 최해미)
    PT_CLASS_NOT_FOUND(404, "PtClass not found"),
    PT_LIVE_NOT_FOUND(404, "PtLive not found"),
    PT_CLASS_TRAINER_MISMATCH(403, "요청 트레이너의 수업이 아닙니다."),
    PT_CLASS_RESERVATION_EXIST(406, "이미 예약된 수업입니다."),

    TRAINER_EXISTS(409, "Trainer already exists"),

    USER_NOT_RESERVED_PT_CLASS(404, "User has not reserved pt class");

    /* 참고용
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    MEMBER_REDIRECTION_LOGIN_SUCCESS(303, "Member redirection login success"),
    MEMBER_REDIRECTION_FIND_PASSWORD(303, "Member redirection find password"),
    MEMBER_NAME_EXISTS(409, "Member name exist"),
    MEMBER_UNAUTHORIZED(401, "Member unauthorized"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    QUESTION_NOT_FOUND(404, "Question not found");
     */

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
