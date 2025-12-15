package com.example.demoopenai.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String question;

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
