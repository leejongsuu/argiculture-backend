package com.aivle.agriculture.domain.chat.service;

import com.aivle.agriculture.domain.chat.dto.ConversationContext;
import com.aivle.agriculture.domain.chat.entity.ChatMessage;
import com.aivle.agriculture.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationContextManager {

    private final ChatMessageRepository chatMessageRepository;
    private static final int RECENT_MESSAGE_LIMIT = 10;
    private static final int SUMMARY_THRESHOLD = 20;

    @Cacheable(value = "conversation", key = "#conversationId")
    public ConversationContext getContext(String conversationId) {
        List<ChatMessage> recentMessages = chatMessageRepository
            .findByConversationIdOrderByTimestampDesc(conversationId, PageRequest.of(0, RECENT_MESSAGE_LIMIT));

        int totalCount = getTotalMessageCount(conversationId);

        String summary = null;
        if (totalCount > SUMMARY_THRESHOLD) {
            summary = generateSummary(conversationId);
        }

        return ConversationContext.builder()
            .conversationId(conversationId)
            .recentMessages(recentMessages)
            .summary(summary)
            .totalMessageCount(totalCount)
            .build();
    }

    public CompletableFuture<ConversationContext> getContextAsync(String conversationId) {
        return CompletableFuture.supplyAsync(() -> getContext(conversationId));
    }

    private int getTotalMessageCount(String conversationId) {
        return chatMessageRepository.countByConversationId(conversationId);
    }

    private String generateSummary(String conversationId) {
        List<ChatMessage> olderMessages = chatMessageRepository
            .findOlderMessages(conversationId, RECENT_MESSAGE_LIMIT, PageRequest.of(0, 20));

        if (olderMessages.isEmpty()) {
            return null;
        }

        String conversationText = olderMessages.stream()
            .map(msg -> msg.getRole() + ": " + msg.getContent())
            .collect(Collectors.joining("\n"));

        return summarizeText(conversationText);
    }

    private String summarizeText(String text) {
        if (text.length() <= 500) {
            return text;
        }

        String[] sentences = text.split("\\.");
        if (sentences.length <= 3) {
            return text;
        }

        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < Math.min(3, sentences.length); i++) {
            if (!sentences[i].trim().isEmpty()) {
                summary.append(sentences[i].trim()).append(". ");
            }
        }

        return summary.toString();
    }

    public void evictCache(String conversationId) {
        log.debug("Evicting conversation cache for: {}", conversationId);
    }
}