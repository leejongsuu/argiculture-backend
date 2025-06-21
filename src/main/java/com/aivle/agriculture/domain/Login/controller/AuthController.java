package com.aivle.agriculture.domain.Login.controller;

import com.aivle.agriculture.domain.Login.oauth.GoogleOAuthClient;
import com.aivle.agriculture.domain.Login.oauth.KakaoOAuthClient;
import com.aivle.agriculture.domain.Login.dto.OAuthAttributes;
import com.aivle.agriculture.domain.Login.entity.User;
import com.aivle.agriculture.domain.Login.oauth.NaverOAuthClient;
import com.aivle.agriculture.domain.Login.repository.UserRepository;
import com.aivle.agriculture.global.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class AuthController {

    private final KakaoOAuthClient kakaoOAuthClient;
    private final NaverOAuthClient naverOAuthClient;
    private final GoogleOAuthClient googleOAuthClient;
    private final JwtToken jwtToken;
    private final UserRepository userRepository;

    @PostMapping("/callback")
    public ResponseEntity<?> callback(
            @RequestParam String registrationId,
            @RequestParam String code // RequestBody에서 RequestParam으로 변경
    ) {
        OAuthAttributes attributes;

        switch (registrationId) {
            case "kakao":
                attributes = kakaoOAuthClient.getUserAttributes(code);
                break;
            case "naver":
                attributes = naverOAuthClient.getUserAttributes(code);
                break;
            case "google":
                attributes = googleOAuthClient.getUserAttributes(code);
                break;
            default:
                throw new IllegalArgumentException("지원하지 않는 로그인입니다: " + registrationId);
        }

        User user = userRepository.findByEmail(attributes.getEmail())
                .orElseGet(() -> userRepository.save(attributes.toEntity()));

        String token = jwtToken.generateToken(user.getEmail());

        return ResponseEntity.ok(Map.of(
                "result", Map.of(
                        "jwt", token,
                        "name", user.getName()
                )
        ));
    }

}


