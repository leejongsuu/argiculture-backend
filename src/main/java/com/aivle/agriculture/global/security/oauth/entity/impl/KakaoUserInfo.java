package com.aivle.agriculture.global.security.oauth.entity.impl;


import com.aivle.agriculture.global.security.oauth.entity.Oauth2UserInfo;

import java.util.Map;

public class KakaoUserInfo implements Oauth2UserInfo {
    private final Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getName() {
        Map<String, Object> props = (Map<String, Object>) attributes.get("properties");
        return (String) props.get("nickname");
    }
}
