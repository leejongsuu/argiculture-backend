package com.aivle.agriculture.domain.chat.service;

import com.aivle.agriculture.domain.chat.dto.ChatResponse;

public interface ChatService {
    ChatResponse ask(String convId, String question);
}
