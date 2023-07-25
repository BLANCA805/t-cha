package com.tcha.auth.config;

import com.tcha.auth.JwtHandler.UserAccessDeniedHandler;
import com.tcha.auth.JwtHandler.UserAuthenticationFailureHandler;
import com.tcha.auth.JwtHandler.UserAuthenticationSuccessHandler;
import com.tcha.auth.OAuth2Handler.OAuth2UserSuccessHandler;
import com.tcha.auth.exception.UserAuthenticationEntryPoint;
import com.tcha.auth.filter.JwtAuthenticationFilter;
import com.tcha.auth.filter.JwtVerificationFilter;
import com.tcha.auth.jwt.JwtTokenizer;
import com.tcha.auth.utils.CustomAuthorityUtils;
import com.tcha.user.service.UserService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
        HeadersConfigurer<HttpSecurity> disable = http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)// 세션 정책 : 세션을 생성하지 않도록 설정 => JWT 사용 환경에선 세션을 사용하지 않음(SecurityContext 정보를 얻을 때 Session 을 사용하지 않음)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new UserAuthenticationEntryPoint()) // 인증서 예외 발생 시 거치는 핸들러(401 Unauthorized)
                .accessDeniedHandler(new UserAccessDeniedHandler()) // 인증서는 정상이지만, 올바르지 않은 권한으로 요청 시 거치는 핸들러(403 Forbidden)
                .and()
                .apply(new CustomFilterConfigurer()) // 커스텀 필터 추가
                .and()
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers( "/users/**").hasAnyRole("USER")
                                .requestMatchers( "/userProfiles/**").hasAnyRole("USER")
                                .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2.successHandler(
                        new OAuth2UserSuccessHandler(jwtTokenizer, authorityUtils, userService)))
                .headers().frameOptions().disable();// H2 콘솔 사용을 위해 X-Frame-Options 비활성화
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    //CustomFilterConfigurer => custom filter 를 filter chain 에 추가 하기 위한 용도
    public class CustomFilterConfigurer extends
            AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class); //AuthenticationManager 객체를 얻음

            //Authentication
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer, userService); // JwtAuthenticationFilter 에 AuthenticationManager 와 JwtTokenizer 를 DI
            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login"); // default URL : "/login"
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler()); // 인증 성공 시 실행할 핸들러 추가
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler()); // 인증 실패 시 실행할 핸들러 추가

            //Verification
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class) // JwtAuthenticationFilter 뒤에 jwtVerificationFilter 위치
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class); //
//            builder.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }
}
