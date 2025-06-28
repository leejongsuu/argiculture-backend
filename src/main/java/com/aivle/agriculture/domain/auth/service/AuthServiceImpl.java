package com.aivle.agriculture.domain.auth.service;

import com.aivle.agriculture.domain.auth.entity.User;
import com.aivle.agriculture.global.exception.CustomException;
import com.aivle.agriculture.global.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.aivle.agriculture.global.response.ErrorCode.BAD_REQUEST;
import static com.aivle.agriculture.global.response.ErrorCode.UNAUTHORIZED;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Map<String, String> AUTH_URI = Map.of(
            "kakao", "/oauth2/authorization/kakao",
            "naver", "/oauth2/authorization/naver",
            "google", "/oauth2/authorization/google"
    );

    @Override
    @Transactional(readOnly = true)
    public String login(String provider) {
        String uri = AUTH_URI.get(provider);
        if (uri == null) {
            throw new CustomException(BAD_REQUEST, "지원하지 않는 OAuth2 공급자입니다: " + provider);
        }
        return uri;
    }

    @Override
    public UserPrincipal getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof UserPrincipal)) {
            throw new CustomException(UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return (UserPrincipal) auth.getPrincipal();
    }

    @Override
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}

