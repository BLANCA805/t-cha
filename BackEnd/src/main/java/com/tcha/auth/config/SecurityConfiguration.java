package com.tcha.auth.config;

import com.tcha.auth.OAuth2Handler.OAuth2UserSuccessHandler;
import com.tcha.auth.jwt.JwtTokenizer;
import com.tcha.auth.utils.CustomAuthorityUtils;
import com.tcha.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
    private String clientSecret;

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final UserService userService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers( HttpMethod.GET, "/h2/**").permitAll() // 적용이 안 됨
//                        .anyRequest().authenticated()
                                .anyRequest().permitAll()
                )
//                .oauth2Login(withDefaults())
                .oauth2Login(oauth2 -> oauth2.successHandler(
                        new OAuth2UserSuccessHandler(jwtTokenizer, authorityUtils, userService)))
                .headers().frameOptions().disable(); // H2 콘솔 사용을 위해 X-Frame-Options 비활성화
        return http.build();
    }
}
