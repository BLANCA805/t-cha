package com.tcha.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenizer {

    @Getter
    private String secretKey = "tempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecrettempSecret";
    @Getter
    private int accessTokenExpirationMinutes = 10;
    @Getter
    private int refreshTokenExpirationMinutes = 10;


    //키 인코딩 메서드
    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    //JWT 에 사용할 signature 을 만드는 메서드
    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

    //accessToken 생성 메서드
    public String generateAccessToken(Map<String, Object> claims,
            String subject,
            Date expiration,
            String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    //refreshToken 생성 메서드
    public String generateRefreshToken(String subject, Date expiration,
            String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    //claim(사용자 정보) 정보 추출 메서드
    public Jws<Claims> getClaims(String jws,String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey); // base64로 인코딩 된 비밀키를 signature 에 사용된 hmacSha 로 변경

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key) // hmacSha key로 JWT 해체 시도, 성공 시 검증 완료 실패 시 예외 발생
                .build()
                .parseClaimsJws(jws);

        return claims;
    }

    // JWT 검증 메서드
    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    // JWT 만료 기간 생성 메서드
    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }
}
