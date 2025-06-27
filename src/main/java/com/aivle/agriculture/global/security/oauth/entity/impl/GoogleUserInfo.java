package com.aivle.agriculture.global.security.oauth.entity.impl;


import com.aivle.agriculture.global.security.oauth.entity.Oauth2UserInfo;

import java.util.Map;

public class GoogleUserInfo implements Oauth2UserInfo {
    private final Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("sub").toString();
    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
