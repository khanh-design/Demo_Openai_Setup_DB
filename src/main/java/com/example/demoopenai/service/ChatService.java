package com.example.demoopenai.service;

import com.example.demoopenai.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    public String generate(ChatRequest request) {
        Prompt prompt = new Prompt(
                new SystemMessage("You are Deveria.AI" +
                        "You should response with a formal voice"),
                new UserMessage(request.getQuestion())
        );

        return chatClient
                .prompt(prompt)
                .call()
                .content();
    }
}
