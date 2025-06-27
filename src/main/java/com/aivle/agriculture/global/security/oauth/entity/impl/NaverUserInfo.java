package com.aivle.agriculture.global.security.oauth.entity.impl;


import com.aivle.agriculture.global.security.oauth.entity.Oauth2UserInfo;

import java.util.Map;

public class NaverUserInfo implements Oauth2UserInfo {
    private final Map<String, Object> response;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.response = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProviderId() {
        return response.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getName() {
        return (String) response.get("name");
    }
}
