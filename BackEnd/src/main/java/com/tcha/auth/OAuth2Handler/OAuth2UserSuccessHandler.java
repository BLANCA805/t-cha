package com.tcha.auth.OAuth2Handler;

import com.tcha.auth.jwt.JwtTokenizer;
import com.tcha.auth.utils.CustomAuthorityUtils;
import com.tcha.user.entity.User;
import com.tcha.user.service.UserService;
import com.tcha.user_profile.entity.UserProfile;
import com.tcha.user_profile.service.UserProfileService;
import com.tcha.utils.exceptions.business.BusinessLogicException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Slf4j
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    // SimpleUrlAuthenticationSuccessHandler : getRedirectStrategy().sendRedirect() 같은 API 사용을 위해

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final UserService userService;
    private final UserProfileService userProfileService;

    //onAuthenticationSuccess : 인증 성공 후, 로그를 기록하거나 사용자 정보를 response로 전송하는 등의 추가 작업을 할 수 있다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        var oAuth2User = (OAuth2User) authentication.getPrincipal(); // OAuth2 인증을 통해 사용자 정보 추출
        String email = String.valueOf(
                oAuth2User.getAttributes().get("email")); // 사용자 정보 중 email 추출(username)
        String name = String.valueOf(oAuth2User.getAttributes().get("name")); // 사용자 정보 중 name 추출
        List<String> authorities = null; // 어플리케이션에서 사용할 인증서에 역할을 생성
        //TODO.
        try { //email, 혹은 기타 검증을 통해 첫 로그인(회원가입)인지 아닌지를 판단
            User isNew = userService.findByEmail(email);

            authorities = isNew.getRoles(); // 기존에 db에 저장된 권한 호출
        } catch (BusinessLogicException e) {//판단 이후 회원가입 로직을 진행시킬지 로그인 진행을 시킬지 선택
            User firstUser = new User();
            authorities = authorityUtils.createRoles(email); // 새로운 권한 생성

            firstUser.setEmail(email);
            firstUser.setRoles(authorities);
            User savedUser = userService.createUser(firstUser);

            UserProfile firstProfile = new UserProfile();

            firstProfile.setName(name);
            firstProfile.setUser(savedUser);

            userProfileService.createUserProfile(firstProfile);
        }
        redirect(request, response, email, authorities); //
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String username
            , List<String> authorities) throws IOException {
        log.info("Token 생성 시작");
        String accessToken = delegateAccessToken(username, authorities); // username : email
        String refreshToken = delegateRefreshToken(username);
        String uri = createURI(accessToken, refreshToken, username).toString();
        getRedirectStrategy().sendRedirect(request, response,
                uri); // 생성한 토큰을 URI에 담고 다시 front 애플리케이션으로 redirect 실행
    }

    //AccessToken 생성
    private String delegateAccessToken(String username, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("roles", authorities);
        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(
                jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
                jwtTokenizer.getSecretKey());
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration,
                base64EncodedSecretKey);
        return accessToken;
    }

    //RefreshToken 생성
    private String delegateRefreshToken(String username) {
        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(
                jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(
                jwtTokenizer.getSecretKey());
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration,
                base64EncodedSecretKey);
        return refreshToken;
    }

    //front로 redirect 하는 부분
    private URI createURI(String accessToken, String refreshToken, String email) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);
        queryParams.add("email", email);

        return UriComponentsBuilder // 별도 설정이 없을 경우 uri 기본 port 값은 80
                .newInstance()
                .scheme("https")
                .host("www.tcha.site")
                .port(443)
                .path("/get_token")
                .queryParams(queryParams)
                .build()
                .toUri();
    }

//    private User saveUser(String email, String password) {
//        User user = new User(email, password);
//        return userService.createUser(user);
//    }
}
