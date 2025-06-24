package com.aivle.agriculture.domain.chat.service;

import com.aivle.agriculture.domain.chat.dto.ChatRequest;
import com.aivle.agriculture.domain.chat.dto.ChatResponse;
import com.aivle.agriculture.domain.common.client.FastApiClient;
import com.aivle.agriculture.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import static com.aivle.agriculture.global.response.ErrorCode.INTERNAL_SERVER_ERROR;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final FastApiClient fastApiClient;

    @Override
    public Mono<ChatResponse> ask(ChatRequest request) {
        return fastApiClient.askRag(request.question())
                .onErrorMap(error -> {
//                    if (error instanceof SomeSpecificException) {
//                        return new CustomException(ErrorCode.INVALID_INPUT);
//                    }
                    return new CustomException(INTERNAL_SERVER_ERROR, error);
                });
    }
}
