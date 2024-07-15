package com.schalljan.model.completion.generate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.schalljan.model.actions.OllamaAction;
import com.schalljan.model.completion.Completion;
import com.schalljan.utils.request.JsonBuilder;
import com.schalljan.utils.request.Payload;
import com.schalljan.utils.request.PayloadBuilder;
import com.schalljan.utils.request.RequestThread;
import com.schalljan.utils.Ollama;
import com.schalljan.utils.stream.JsonStreamHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OllamaCompletion extends Completion implements Runnable {
    private final Payload payload;
    private String response;
    private final Ollama host;
    private final JsonStreamHandler handler;

    public OllamaCompletion(Builder builder) {
        this.host = builder.getHost();
        this.payload = builder.payloadBuilder.build();
        this.handler = builder.getHandler();
    }

    @Override
    public void run() {
        try {
            String json = new JsonBuilder(payload).getJsonAsString();
            response = OllamaAction.COMPLETION(host, json, handler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OllamaCompletion execute() {
        RequestThread.execute(this);
        return this;
    }

    @Override
    public OllamaResponse getResponse() {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            return mapper.readValue(response, OllamaResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getJson() {
        return new JsonBuilder(payload).getJsonAsString();
    }

    public static class Builder extends Completion.Builder<Builder> {
        private final PayloadBuilder payloadBuilder;

        public Builder(Ollama host, JsonStreamHandler handler) {
            super(host, handler);
            payloadBuilder = super.getPayloadBuilder();
        }

        public Builder(Ollama host) {
            super(host);
            payloadBuilder = super.getPayloadBuilder();
        }

        public Builder setModel(String model) {
            payloadBuilder.setModel(model);
            return this;
        }

        public Builder setPrompt(String prompt) {
            payloadBuilder.setPrompt(prompt);
            return this;
        }

        public Builder setImages(List<File> images) {
            payloadBuilder.setImages(images);
            return this;
        }

        @Override
        public OllamaCompletion build() {
            return new OllamaCompletion(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
