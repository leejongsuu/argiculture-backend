package com.aivle.agriculture.domain.chat.service;

import com.aivle.agriculture.domain.chat.dto.ChatResponse;
import com.aivle.agriculture.domain.chat.dto.ConversationContext;
import com.aivle.agriculture.domain.chat.dto.RagPayload;
import com.aivle.agriculture.domain.chat.entity.ChatMessage;
import com.aivle.agriculture.domain.chat.entity.Role;
import com.aivle.agriculture.domain.chat.repository.ChatMessageRepository;
import com.aivle.agriculture.domain.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    //    private final UserRepository userRepository;
    private final FastApiClient fastApiClient;

    @Override
    public ChatResponse ask(String convId, String question) {

//        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//        User user = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new CustomException(NOT_FOUND, "유저를 찾을 수 없습니다."));
        User user = null;

        ChatMessage userMessage = ChatMessage.builder()
                .conversationId(convId)
                .role(Role.USER)
                .content(question)
                .user(user)
                .build();

        chatMessageRepository.save(userMessage);

        List<ChatMessage> history = chatMessageRepository.findRecentMessages(convId, PageRequest.of(0, 7));

        Collections.reverse(history);

        String context = history.stream()
                .map(msg -> msg.getRole() + ": " + msg.getContent())
                .collect(Collectors.joining("\n"));

        RagPayload payload = RagPayload.builder()
                .conversationId(convId)
                .context(context)
                .question(question)
                .build();

        ChatResponse chatResponse = fastApiClient.askRag(payload);

        ChatMessage botMsg = ChatMessage.builder()
                .conversationId(convId)
                .role(Role.ASSISTANT)
                .content(chatResponse.answer())
                .user(user)
                .build();

        chatMessageRepository.save(botMsg);

        return chatResponse;
    }

    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    @Override
    public CompletableFuture<String> processMessageAsync(String question, ConversationContext context) {
        return CompletableFuture.supplyAsync(() -> {
            String contextString = buildContextString(context);

            RagPayload payload = RagPayload.builder()
                .conversationId(context.getConversationId())
                .context(contextString)
                .question(question)
                .build();

            ChatResponse response = fastApiClient.askRag(payload);
            return response.answer();
        });
    }

    private String buildContextString(ConversationContext context) {
        StringBuilder contextBuilder = new StringBuilder();

        if (context.getSummary() != null && !context.getSummary().isEmpty()) {
            contextBuilder.append("이전 대화 요약: ").append(context.getSummary()).append("\n\n");
        }

        if (context.getRecentMessages() != null && !context.getRecentMessages().isEmpty()) {
            List<ChatMessage> messages = context.getRecentMessages();
            Collections.reverse(messages);

            String recentContext = messages.stream()
                .map(msg -> msg.getRole() + ": " + msg.getContent())
                .collect(Collectors.joining("\n"));

            contextBuilder.append("최근 대화:\n").append(recentContext);
        }

        return contextBuilder.toString();
    }
}