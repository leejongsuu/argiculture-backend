package com.aivle.agriculture.global.security.oauth.service;

import com.aivle.agriculture.domain.auth.entity.User;
import com.aivle.agriculture.domain.auth.repository.UserRepository;
import com.aivle.agriculture.global.exception.CustomException;
import com.aivle.agriculture.global.security.UserPrincipal;
import com.aivle.agriculture.global.security.oauth.entity.Oauth2UserInfo;
import com.aivle.agriculture.global.security.oauth.entity.impl.GoogleUserInfo;
import com.aivle.agriculture.global.security.oauth.entity.impl.KakaoUserInfo;
import com.aivle.agriculture.global.security.oauth.entity.impl.NaverUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.aivle.agriculture.global.response.ErrorCode.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final DefaultOAuth2UserService delegate;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = delegate.loadUser(request);
        String provider = request.getClientRegistration().getRegistrationId();
        Oauth2UserInfo info = extractUserInfo(provider, oAuth2User.getAttributes());
        User user = findOrCreateUser(info);
        return new UserPrincipal(user, oAuth2User.getAttributes(), info.getProviderId());
    }

    private Oauth2UserInfo extractUserInfo(String provider, Map<String, Object> attrs) {
        return switch (provider) {
            case "kakao" -> new KakaoUserInfo(attrs);
            case "naver" -> new NaverUserInfo(attrs);
            case "google" -> new GoogleUserInfo(attrs);
            default -> throw new CustomException(BAD_REQUEST, "지원하지 않는 OAuth2 제공자: " + provider);
        };
    }

    @Transactional
    public User findOrCreateUser(Oauth2UserInfo info) {
        return userRepository.findByProviderAndProviderId(
                        info.getProvider(), info.getProviderId())
                .orElseGet(() -> createUser(info));
    }

    private User createUser(Oauth2UserInfo info) {
        User user = User.builder()
                .name(info.getName())
                .provider(info.getProvider())
                .providerId(info.getProviderId())
                .build();
        return userRepository.save(user);
    }
}

