package com.demo.nimn.dto.chatgpt;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRequest {
    private String model;
    private List<ChatMessage> messages;

    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.messages =  new ArrayList<>();
        this.messages.add(new ChatMessage("user", prompt));
    }
}