package com.example.demoopenai.controller;

import com.example.demoopenai.dto.BillItem;
import com.example.demoopenai.dto.ChatRequest;
import com.example.demoopenai.dto.ExpenseInfo;
import com.example.demoopenai.dto.FilmInfo;
import com.example.demoopenai.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat-message")
    public ExpenseInfo sendMessage(@RequestBody ChatRequest chatRequest) {
        return chatService.generate(chatRequest);
    }

    @PostMapping("/chat-with-image")
    public List<BillItem> chatWithImage(@RequestParam("file") MultipartFile file,
                                        @RequestParam("message") String message) {
        return chatService.chatWithImage(file, message);
    }
}
