package com.example.demoopenai.service;

import com.example.demoopenai.dto.BillItem;
import com.example.demoopenai.dto.ChatRequest;
import com.example.demoopenai.dto.ExpenseInfo;
import com.example.demoopenai.dto.FilmInfo;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ChatService {
    private final ChatClient chatClient;

    private final JdbcChatMemoryRepository jdbcChatMemoryRepository;

    public ChatService(ChatClient.Builder chatClient, JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        this.jdbcChatMemoryRepository = jdbcChatMemoryRepository;

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(30)
                .build();

        this.chatClient = chatClient
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public String generate(ChatRequest request) {
        String conversationId = "conversation1";
        Prompt prompt = new Prompt(
                new SystemMessage("You are Deveria.AI" +
                        "You should response with a formal voice"),
                new UserMessage(request.getQuestion())
        );

        return chatClient
                .prompt(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }

    public List<BillItem> chatWithImage(MultipartFile file, String message) {
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
                .entity(new ParameterizedTypeReference<List<BillItem>>() {
                });
    }
}
