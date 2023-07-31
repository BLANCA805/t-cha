package com.tcha.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcha.auth.dto.LoginDto;
import com.tcha.auth.jwt.JwtTokenizer;
import com.tcha.user.entity.User;
import com.tcha.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //UsernamePasswordAuthenticationFilter : Username/Password 기반의 인증을 처리
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;
    private final UserService userService;

    // attemptAuthentication() : 내부에서 인증을 하기 위한 메소드
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper(); // request로 전달받은 username(email)과 password를 dto로 역직렬화하기 위함.
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                        loginDto.getPassword()); // 인증 처리를 위해 token 생성
        return authenticationManager.authenticate(
                authenticationToken); // 생성한 token 을 authenticationManager 에게 전달하여 인증 처리를 위임
    }

    //successfulAuthentication() : 클라이언트의 인증 정보를 이용해 인증에 성공하면 호출될 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws ServletException, IOException {
        User user = (User) authResult.getPrincipal(); // Authentication 에 담긴 principal(사용자 정보, User 객체)호출
        String accessToken = delegateAccessToken(user); // AccessToken 생성
        String refreshToken = delegateRefreshToken(user); // RefreshToken
        response.setHeader("Authorization",
                "Bearer " + accessToken); // Response Header에 AccessToken 추가 + Bearer 명시
        response.setHeader("Refresh", refreshToken); // Response Header에 RefreshToken 추가
        String authUserId = userService.findByEmail(user.getEmail()).getId().toString();
        response.setHeader("Id", authUserId);

        this.getSuccessHandler().onAuthenticationSuccess(request, response,
                authResult); // 인증 성공 시 필터에서 successHandler 호출
        // 인증 실패 시에는 별도의 코드가 없더라도 failureHandler 자동으로 호출됨
    }

    //delegateAccessToken() : 인증된 principal(user) 정보로 AccessToken 생성
    private String delegateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getEmail());
        claims.put("roles", user.getRoles());
        String subject = user.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(
                jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
                jwtTokenizer.getSecretKey());
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration,
                base64EncodedSecretKey);
        return accessToken;
    }

    //delegateRefreshToken() : 인증된 principal(user) 정보로 RefreshToken 생성
    private String delegateRefreshToken(User user) {
        String subject = user.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(
                jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
                jwtTokenizer.getSecretKey());
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration,
                base64EncodedSecretKey);
        return refreshToken;
    }
}
