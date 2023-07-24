package com.tcha.auth.filter;

import com.tcha.auth.jwt.JwtTokenizer;
import com.tcha.auth.utils.CustomAuthorityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
//OncePerRequestFilter : request 당 한 번 실행되는 필터
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer; //JWT 검증 및 Claims 추출
    private final CustomAuthorityUtils authorityUtils; //검증에 성공 후 Authentication 객체에 사용자 권한 채우기
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request); // JWT 검증 (JWS; JWT Signed)
            setAuthenticationToContext(claims); // SecurityContext 에 Authentication 객체 저장
        } catch (SignatureException se) { // 예외 발생 시 필터를 타고타고 진행 후 AuthenticationEntryPoint 에 도달
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }


        filterChain.doFilter(request, response); // 문제없이 필터가 진행됐을 경우, 다음 필터 호출
    }

    //shouldNotFilter : 특정 조건에 부합되면 다음 Filter 로 건너뛰게하는 Filter
    //자격 증명이 필요하지 않은 리소스 요청이 들어온 경우 실행될 필터
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer"); // 토큰이 없거나, 인증 타입이 "Bearer"아닌 경우 JwtVerificationFilter 를 건너뛴다.
    }

    // request Header 에서 토큰을 추출하고, 해당 토큰을 검증 및 claims(유저 정보)를 추출하는 메소드
    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", ""); // header 에 존재하는 Bearer 제거
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey()); // JwtTokenizer 객체 필드로 존재하는 secret 키 호출 및 인코딩 키 생성
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody(); // Claims 파싱 => 정상 파싱은 곧 검증 성공을 의미

        return claims;
    }


    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username"); //파싱된 claims에서 username 추출
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List) claims.get("roles")); // List<GrantedAuthority> 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities); // username 과 List<GrantedAuthority> 를 기반으로 Authentication 객체 생성
        SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext 에 Authentication 객체 저장
    }
}
