package com.aivle.agriculture.domain.chat.repository;

import com.aivle.agriculture.domain.chat.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE m.conversationId = :convId ORDER BY m.createdAt DESC")
    List<ChatMessage> findRecentMessages(String convId, Pageable pageable);

    @Query("SELECT m FROM ChatMessage m WHERE m.conversationId = :conversationId ORDER BY m.createdAt DESC")
    List<ChatMessage> findByConversationIdOrderByTimestampDesc(String conversationId, Pageable pageable);

    @Query("SELECT COUNT(m) FROM ChatMessage m WHERE m.conversationId = :conversationId")
    int countByConversationId(String conversationId);

    @Query("SELECT m FROM ChatMessage m WHERE m.conversationId = :conversationId ORDER BY m.createdAt ASC LIMIT :offset, :limit")
    List<ChatMessage> findOlderMessages(String conversationId, int offset, Pageable pageable);
}
