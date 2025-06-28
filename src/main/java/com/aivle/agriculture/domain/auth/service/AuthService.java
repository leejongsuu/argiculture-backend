package com.aivle.agriculture.domain.auth.service;

import com.aivle.agriculture.global.security.UserPrincipal;

public interface AuthService {
    String login(String provider);

    Long getCurrentUserId();

    UserPrincipal getCurrentUser();
}
