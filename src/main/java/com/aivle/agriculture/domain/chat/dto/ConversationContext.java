package com.aivle.agriculture.domain.chat.dto;

import com.aivle.agriculture.domain.chat.entity.ChatMessage;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ConversationContext {
    private final String conversationId;
    private final List<ChatMessage> recentMessages;
    private final String summary;
    private final int totalMessageCount;
}