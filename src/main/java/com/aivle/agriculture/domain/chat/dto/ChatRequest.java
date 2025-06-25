package com.aivle.agriculture.domain.chat.dto;

import jakarta.annotation.Nullable;

public record ChatRequest(@Nullable String conversationId, String question) {
}
