package com.tcha.pt_class.dto;


import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class PtClassDto {

//    @Getter
//    public static class Post {
//
//        private LocalDateTime startAt; // pt 시작 시간
//    }

    @Getter
    public static class Patch {

        @NotBlank
        private Long classId;
        private LocalDateTime startAt; // 변경할 시간
    }

    @Getter
    @Builder
    public static class Response {

        
    }
}
