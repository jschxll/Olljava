package com.schalljan.model.completion.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schalljan.utils.Ollama;
import com.schalljan.utils.stream.JsonStreamHandler;
import junit.framework.TestCase;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ChatCompletionTest extends TestCase {
    public void testGetResponse() {
        Ollama host = new Ollama("http://localhost:11434");
        ChatCompletion completion = new ChatCompletion.Builder(host)
                .setModel("llava")
                .setStreaming(false)
                .setMessages(List.of(new Message(ChatRoles.USER, "What do you see on this picture?",
                                List.of(new File("C:\\Users\\Jan Schall\\Pictures\\Wallpaper.jpg")))))
                .build();
        ChatResponse response = completion.execute().getResponse();
        System.out.println(response.getContent());
    }

    public void testGetResponseStreaming() {
        Ollama host = new Ollama("http://localhost:11434");
        JsonStreamHandler handler = s -> {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node;
            try {
                node = mapper.readTree(s);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            String content = String.valueOf(node.get("message").get("content"));
            content = content.substring(1, content.length()-1);
            System.out.println(content);
        };

        ChatCompletion completion = new ChatCompletion.Builder(host, handler)
                .setModel("llava")
                .setMessages(List.of(new Message(ChatRoles.USER, "What do you see on this picture?",
                        List.of(new File("C:\\Users\\Jan Schall\\Pictures\\Wallpaper.jpg")))))
                .build();
        completion.execute();
    }
}