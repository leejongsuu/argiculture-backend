package com.aivle.agriculture.domain.chat.repository;

import com.aivle.agriculture.domain.chat.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE m.conversationId = :convId ORDER BY m.createdAt DESC")
    List<ChatMessage> findRecentMessages(String convId, Pageable pageable);
}
