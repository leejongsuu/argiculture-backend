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
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Oauth2UserInfo userInfo = switch (provider) {
            case "kakao" -> new KakaoUserInfo(attributes);
            case "naver" -> new NaverUserInfo(attributes);
            case "google" -> new GoogleUserInfo(attributes);
            default -> throw new CustomException(BAD_REQUEST, "지원하지 않는 OAuth2 제공자입니다: " + provider);
        };

        User user = userRepository.findByProviderAndProviderId(
                userInfo.getProvider(), userInfo.getProviderId()
        ).orElseGet(() -> {
            User u = User.builder()
                    .name(userInfo.getName())
                    .provider(userInfo.getProvider())
                    .providerId(userInfo.getProviderId())
                    .build();
            return userRepository.save(u);
        });

        return new UserPrincipal(user, attributes, userInfo.getProviderId());
    }
}

