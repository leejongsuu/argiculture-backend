package com.aivle.agriculture.domain.chat.service;

import com.aivle.agriculture.domain.chat.dto.ChatResponse;
import com.aivle.agriculture.domain.chat.dto.RagPayload;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FastApiClient {
    private final WebClient webClient;

    public FastApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ChatResponse askRag(RagPayload ragPayload) {
        return webClient.post()
                .uri("/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ragPayload)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();
    }
}


