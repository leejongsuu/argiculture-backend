package com.aivle.agriculture.domain.chat.service;

import com.aivle.agriculture.domain.chat.dto.ChatResponse;
import com.aivle.agriculture.domain.chat.dto.ConversationContext;
import com.aivle.agriculture.domain.chat.entity.ChatMessage;

import java.util.concurrent.CompletableFuture;

public interface ChatService {
    ChatResponse ask(String convId, String question);
    ChatMessage saveMessage(ChatMessage message);
    CompletableFuture<String> processMessageAsync(String question, ConversationContext context);
}
