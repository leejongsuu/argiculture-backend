package com.aivle.agriculture.domain.test.dto;

import lombok.Builder;

@Builder
public record TestResponse(Long id, String name, String description) {
}
