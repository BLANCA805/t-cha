package com.tcha.auth.JwtHandler;

import com.nimbusds.jose.shaded.gson.Gson;
import com.tcha.utils.exceptions.controller.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

//MemberAuthenticationFailureHandler : AuthenticationFailureHandler 를 구현하여 커스텀
//AuthenticationFailureHandler : 인증 실패시 HttpServletResponse 로 출력 스트림을 생성하여 response 를 전달 할 수 있음
@Slf4j
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 인증 실패 시, 에러 로그를 기록하거나 error response를 전송할 수 있다.
        log.error("# Authentication failed: {}", exception.getMessage());
        sendErrorResponse(response);
    }

    // response stream 에 error 정보를 담는 메소드
    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        Gson gson = new Gson(); // JSON 문자열로의 변환을 위해
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED); // 인증 실패에 대한 상태 코드 전달
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // response header contentType 지정
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // response header status 지정
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class)); // json 문자열로 변환 후 출려 스트림에 담음
    }
}