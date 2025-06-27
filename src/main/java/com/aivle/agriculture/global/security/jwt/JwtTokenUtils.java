package com.aivle.agriculture.global.security.jwt;

import com.aivle.agriculture.global.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.aivle.agriculture.global.response.ErrorCode.INVALID_TOKEN;

@Component
public class JwtTokenUtils {

    private final Key key;
    private final long expirationTimeMillis = 1000L * 60 * 60 * 2; // 2시간

    public JwtTokenUtils(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성
    public String generateAccessToken(String providerId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTimeMillis);

        return Jwts.builder()
                .setSubject(providerId)
                .claim("auth", "ROLE_USER")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Date getTokenExpirationDate() {
        return new Date(System.currentTimeMillis() + expirationTimeMillis);
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new CustomException(INVALID_TOKEN, "토큰이 만료되었습니다.");
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(INVALID_TOKEN, e.getMessage());
        }
    }

    // 인증 객체 추출
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        Object authObj = claims.get("auth");
        if (authObj == null) {
            throw new CustomException(INVALID_TOKEN, "권한 정보가 없는 토큰입니다.");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.stream(authObj.toString().split(","))
                .map(String::trim)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
