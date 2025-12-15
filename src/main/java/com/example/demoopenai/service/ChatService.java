package com.example.demoopenai.service;

import com.example.demoopenai.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    public String generate(ChatRequest request) {
        return chatClient
                .prompt()
                .user(request.getQuestion())
                .call()
                .content();
    }
}
