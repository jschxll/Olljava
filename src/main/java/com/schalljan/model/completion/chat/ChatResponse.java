package com.schalljan.model.completion.chat;

import com.schalljan.model.completion.Response;

public class ChatResponse extends Response {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public String getContent() {
        return getMessage().getContent();
    }

    public String getRole() {
        return getMessage().getRole();
    }
}
