package com.aivle.agriculture.domain.chat.dto;

import lombok.Builder;

@Builder
public record RagPayload(String conversationId, String context, String question) {
}
