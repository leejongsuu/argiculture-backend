package com.aivle.agriculture.domain.chat.controller;

import com.aivle.agriculture.domain.chat.entity.ChatMessage;
import com.aivle.agriculture.domain.chat.service.ChatService;
import com.aivle.agriculture.domain.chat.service.ConversationContextManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatWebSocketHandler {

    private final ChatService chatService;
    private final ConversationContextManager contextManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final ConcurrentHashMap<String, String> sessionConversationMap = new ConcurrentHashMap<>();

    @MessageMapping("/chat.send")
    public void handleMessage(ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        String conversationId = getOrCreateConversationId(sessionId);

        sendStatus(sessionId, "PROCESSING");

        saveUserMessage(message, conversationId)
            .thenCompose(saved -> {
                return contextManager.getContextAsync(conversationId);
            })
            .thenCompose(context -> {
                return chatService.processMessageAsync(message.getContent(), context);
            })
            .thenCompose(response -> {
                return saveBotMessage(response, conversationId);
            })
            .thenAccept(botMessage -> {
                sendResponse(sessionId, botMessage);
                sendStatus(sessionId, "COMPLETED");
            })
            .exceptionally(throwable -> {
                log.error("Error processing message for session {}", sessionId, throwable);
                sendError(sessionId, "메시지 처리 중 오류가 발생했습니다.");
                sendStatus(sessionId, "ERROR");
                return null;
            });
    }

    private String getOrCreateConversationId(String sessionId) {
        return sessionConversationMap.computeIfAbsent(sessionId,
            key -> UUID.randomUUID().toString());
    }

    private CompletableFuture<ChatMessage> saveUserMessage(ChatMessage message, String conversationId) {
        return CompletableFuture.supplyAsync(() -> {
            message.setConversationId(conversationId);
            return chatService.saveMessage(message);
        });
    }

    private CompletableFuture<ChatMessage> saveBotMessage(String response, String conversationId) {
        return CompletableFuture.supplyAsync(() -> {
            ChatMessage botMessage = ChatMessage.builder()
                .conversationId(conversationId)
                .role(com.aivle.agriculture.domain.chat.entity.Role.ASSISTANT)
                .content(response)
                .build();
            return chatService.saveMessage(botMessage);
        });
    }

    private void sendStatus(String sessionId, String status) {
        messagingTemplate.convertAndSendToUser(sessionId, "/queue/status", status);
    }

    private void sendResponse(String sessionId, ChatMessage message) {
        messagingTemplate.convertAndSendToUser(sessionId, "/queue/response", message);
    }

    private void sendError(String sessionId, String errorMessage) {
        messagingTemplate.convertAndSendToUser(sessionId, "/queue/error", errorMessage);
    }
}