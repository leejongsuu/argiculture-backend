package com.aivle.agriculture.domain.chat.controller;

import com.aivle.agriculture.domain.chat.dto.ChatRequest;
import com.aivle.agriculture.domain.chat.dto.ChatResponse;
import com.aivle.agriculture.domain.chat.service.ChatService;
import com.aivle.agriculture.global.response.Response;
import com.aivle.agriculture.global.response.ResponseFactory;
import com.aivle.agriculture.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ResponseFactory responseFactory;
    private final ChatService chatService;

    @PostMapping
    public Mono<Response<ChatResponse>> ask(@RequestBody ChatRequest request) {
        return responseFactory.successMono(SuccessCode.OK, chatService.ask(request));
    }
}
