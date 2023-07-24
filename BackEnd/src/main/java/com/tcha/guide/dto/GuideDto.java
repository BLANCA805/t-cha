package com.tcha.guide.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

public class GuideDto {

    @Getter
    @AllArgsConstructor
    public static class post{

        @NotBlank
        @Pattern(regexp = "([A-Z])(0-9)", message = "코드는 영문 2글자 + 숫자로 구성되어야 합니다. ex) live = LI805 ")
        private String code;
        private String title;

        private String content;

    }

    @Getter
    @AllArgsConstructor
    public static class patch{
        private Long id;
        @NotBlank
        @Pattern(regexp = "([A-Z])(0-9)", message = "코드는 영문 2글자 + 숫자로 구성되어야 합니다. ex) live = LI805 ")
        private String code;

        private String content;

        private String status;
        private String title;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response{
        private Long id;
        private String title;
        private String code;
        private String content;
        private String status;

//        Response(Long id, String title, String code, String content, String status){
//            this.id = id;
//            this.title = title;
//            this.content = content;
//            this.code = code;
//            this.status = status;
//        }
    }

}
