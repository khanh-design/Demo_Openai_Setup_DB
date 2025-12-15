package com.example.demoopenai.controller;

import com.example.demoopenai.dto.ChatRequest;
import com.example.demoopenai.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat-message")
    public String sendMessage(@RequestBody ChatRequest chatRequest) {
        return chatService.generate(chatRequest);
    }
}
