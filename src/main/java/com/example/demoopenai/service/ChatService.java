package com.example.demoopenai.service;

import com.example.demoopenai.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

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

    public String chatWithImage(MultipartFile file, String message) {
        Media media = Media.builder()
                .mimeType(MimeTypeUtils.parseMimeType(file.getContentType()))
                .data(file.getResource())
                .build();

//        AI sẽ tự thảo sức sáng tạo nội dung
        ChatOptions chatOptions = ChatOptions.builder()
                .temperature(1D)
                .build();

        return chatClient.prompt()
                .options(chatOptions)
                .system("you are Derive.AI")
                .user(promptUserSpec
                        -> promptUserSpec.media(media)
                        .text(message))
                .call()
                .content();
    }
}
