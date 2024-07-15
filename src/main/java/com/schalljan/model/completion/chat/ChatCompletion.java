package com.schalljan.model.completion.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.schalljan.model.actions.OllamaAction;
import com.schalljan.model.completion.Completion;
import com.schalljan.utils.request.RequestThread;
import com.schalljan.utils.Ollama;
import com.schalljan.utils.request.JsonBuilder;
import com.schalljan.utils.request.Payload;
import com.schalljan.utils.request.PayloadBuilder;
import com.schalljan.utils.stream.JsonStreamHandler;

import java.io.IOException;
import java.util.List;

public class ChatCompletion extends Completion implements Runnable {
    private String response;
    private final Ollama host;
    private final JsonStreamHandler handler;
    private final Payload payload;

    public ChatCompletion(Builder builder) {
        this.host = builder.getHost();
        this.payload = builder.payloadBuilder.build();
        this.handler = builder.getHandler();
    }

    @Override
    public void run() {
        try {
            String json = new JsonBuilder(payload).getJsonAsString();
            response = OllamaAction.CHAT_COMPLETION(host, json, handler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ChatResponse getResponse() {
        try {
            // Add JavaTimeModule, to parse in the response the finished LLM response to a OffsetDateTime object
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            return mapper.readValue(response, ChatResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ChatCompletion execute() {
        RequestThread.execute(this);
        return this;
    }

    @Override
    public String getJson() {
        return new JsonBuilder(payload).getJsonAsString();
    }

    public static class Builder extends Completion.Builder<Builder> {
        private final PayloadBuilder payloadBuilder;

        public Builder(Ollama host) {
            super(host);
            payloadBuilder = super.getPayloadBuilder();
        }

        public Builder(Ollama host, JsonStreamHandler handler) {
            super(host, handler);
            payloadBuilder = super.getPayloadBuilder();
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder setModel(String model) {
            payloadBuilder.setModel(model);
            return this;
        }

        public Builder setMessages(List<Message> messages) {
            payloadBuilder.setChatMessages(messages);
            return this;
        }

        @Override
        public ChatCompletion build() {
            return new ChatCompletion(this);
        }
    }
}
