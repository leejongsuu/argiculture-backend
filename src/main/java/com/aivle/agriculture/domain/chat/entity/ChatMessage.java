package com.aivle.agriculture.domain.chat.entity;

import com.aivle.agriculture.domain.auth.entity.User;
import com.aivle.agriculture.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "idx_conv_id", columnList = "conversationId"))
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conversationId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public ChatMessage(String conversationId, Role role, String content, User user) {
        this.conversationId = conversationId;
        this.role = role;
        this.content = content;
        this.user = user;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
