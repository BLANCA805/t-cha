package com.tcha.guide.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

public class GuideDto {

    @Getter
    public static class post{

        @NotBlank
        @Pattern(regexp = "([A-Z])(0-9)", message = "코드는 영문 2글자 + 숫자로 구성되어야 합니다. ex) live = LI805 ")
        private String code;

        private String Content;

    }

    @Getter
    public static class patch{
        @NotBlank
        @Pattern(regexp = "([A-Z])(0-9)", message = "코드는 영문 2글자 + 숫자로 구성되어야 합니다. ex) live = LI805 ")
        private String code;

        private String Content;

        private String status;
    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        private String code;
        private String content;
        private String status;

    }

}
