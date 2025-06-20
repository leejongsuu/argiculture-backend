package com.aivle.agriculture.domain.chat.service;

import com.aivle.agriculture.domain.chat.dto.ChatRequest;
import com.aivle.agriculture.domain.chat.dto.ChatResponse;
import reactor.core.publisher.Mono;

public interface ChatService {
    Mono<ChatResponse> ask(ChatRequest request);
}
