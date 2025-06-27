package com.aivle.agriculture.domain.auth.service.dto;

import lombok.Builder;

@Builder
public record TokenInfo(String type, String accessToken, long accessTokenExpiresIn) {
}
