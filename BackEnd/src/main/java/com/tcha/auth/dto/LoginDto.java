package com.tcha.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

//로그인 인증 요청 시, client 의 요청 정보를 담을 dto
//admin 계정용으로 사용할 예정
@Getter
public class LoginDto {
    @NotBlank(message = "공백일 수 없습니다.")
    private String email;
    @Size(min = 8, max = 20, message = "비밀번호 길이는 8 이상 20 이하여야 합니다.")
    private String password;
}
