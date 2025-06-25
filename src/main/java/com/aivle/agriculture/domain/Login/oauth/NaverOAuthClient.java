package com.aivle.agriculture.domain.Login.oauth;

import com.aivle.agriculture.domain.Login.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class NaverOAuthClient {

    private final RestTemplate restTemplate;

    @Value("${oauth.naver.client-id}")
    private String clientId;

    @Value("${oauth.naver.client-secret}")
    private String clientSecret;

    @Value("${oauth.naver.redirect-uri}")
    private String redirectUri;

    public OAuthAttributes getUserAttributes(String code) {
        String accessToken = getAccessToken(code);
        return getUserInfo(accessToken);
    }

    // ✅ 액세스 토큰 요청
    public String getAccessToken(String code) {
        String tokenUri = "https://nid.naver.com/oauth2.0/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, request, Map.class);

        System.out.println("🟢 [Naver Token Response] = " + response.getBody());

        return (String) response.getBody().get("access_token");
    }

    // ✅ 사용자 정보 요청 및 OAuthAttributes 생성
    @SuppressWarnings("unchecked")
    private OAuthAttributes getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                request,
                Map.class
        );

        Map<String, Object> responseBody = response.getBody();
        System.out.println("🟢 [Naver UserInfo Response] = " + responseBody);

        // [핵심 수정!] attributes를 "response"만 넘겨야 함!
        if (responseBody == null || responseBody.get("response") == null) {
            throw new IllegalArgumentException("Naver attributes가 null입니다. 응답: " + responseBody);
        }

        // responseBody.get("response")만 넘겨야 함!
        Map<String, Object> attributes = (Map<String, Object>) responseBody.get("response");

        // [여기에서] attributes를 바로 넘김!
        return OAuthAttributes.of("naver", "id", attributes);
    }
}
