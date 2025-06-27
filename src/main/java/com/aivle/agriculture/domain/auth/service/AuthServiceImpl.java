package com.aivle.agriculture.domain.auth.service;

import com.aivle.agriculture.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.aivle.agriculture.global.response.ErrorCode.NOT_FOUND;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public String login(String provider) {
        if (provider.equals("kakao") || provider.equals("google") || provider.equals("naver")) {
            return "/oauth2/authorization/" + provider;
        }
        throw new CustomException(NOT_FOUND, "provider을 찾을 수 없습니다.");
    }
}
