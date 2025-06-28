package com.aivle.agriculture.global.security;

import com.aivle.agriculture.domain.auth.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class UserPrincipal implements OAuth2User {
    private final User user;
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user, Map<String, Object> attributes, String nameAttributeKey) {
        this.user = user;
        this.attributes = attributes;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        this.nameAttributeKey = nameAttributeKey;
    }

    @Override
    public String getName() {
        return user.getProviderId();
    }

    public Long getId() {
        return user.getId();
    }
}

