package com.aivle.agriculture.global.security.oauth.service;

import com.aivle.agriculture.global.security.UserPrincipal;
import com.aivle.agriculture.global.security.jwt.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenUtils jwtTokenUtils;

    @Value("${url.backend}/auth/success")
    private String successUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        Long userId = user.getId();

        String access = jwtTokenUtils.generateAccessToken(userId);
        Date exp = jwtTokenUtils.getTokenExpirationDate();

        String redirectUrl = UriComponentsBuilder.fromUriString(successUrl)
                .queryParam("type", "Bearer")
                .queryParam("accessToken", access)
                .queryParam("accessExpireDuration", exp.getTime())
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
