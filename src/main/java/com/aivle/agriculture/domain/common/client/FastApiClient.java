package com.aivle.agriculture.domain.common.client;

import com.aivle.agriculture.domain.chat.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class FastApiClient {
    private final WebClient webClient;

    public FastApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ChatResponse> askRag(String question) {
        return webClient.post()
                .uri("/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("question", question))
                .retrieve()
                .bodyToMono(ChatResponse.class);
    }
}


