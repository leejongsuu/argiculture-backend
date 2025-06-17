package com.aivle.agriculture.domain.Login.dto;

import lombok.Getter;
import com.aivle.agriculture.domain.Login.entity.User;
import java.util.Map;

@Getter
public class OAuthAttributes {

    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static com.aivle.agriculture.domain.Login.dto.OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(userNameAttributeName, (Map<String, Object>) attributes.get("response"));
        } else if ("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);  // 기본은 구글
    }

    private static com.aivle.agriculture.domain.Login.dto.OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return new com.aivle.agriculture.domain.Login.dto.OAuthAttributes(
                attributes,
                userNameAttributeName,
                (String) attributes.get("name"),
                (String) attributes.get("email")
        );
    }

    private static com.aivle.agriculture.domain.Login.dto.OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return new com.aivle.agriculture.domain.Login.dto.OAuthAttributes(
                attributes,
                userNameAttributeName,
                (String) attributes.get("name"),
                (String) attributes.get("email")
        );
    }

    private static com.aivle.agriculture.domain.Login.dto.OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return new com.aivle.agriculture.domain.Login.dto.OAuthAttributes(
                attributes,
                userNameAttributeName,
                (String) profile.get("nickname"),
                (String) kakaoAccount.get("email")
        );
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .build();
    }
}