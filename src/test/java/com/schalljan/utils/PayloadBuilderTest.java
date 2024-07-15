package com.schalljan.utils;

import com.schalljan.model.completion.generate.OllamaCompletion;
import com.schalljan.utils.request.PayloadBuilder;
import com.schalljan.utils.stream.StreamHandler;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PayloadBuilderTest extends TestCase {
    public void testPayloadBuilder() {
        Ollama host = new Ollama("http://localhost:11434");
        OllamaCompletion completion = new OllamaCompletion.Builder(host, new StreamHandler())
                .setModel("llava")
                .setPrompt("What do you see on this picture?")
                .setImages(List.of(new File("C:\\Users\\Jan Schall\\Pictures\\Wallpaper.jpg")))
                .build();
        System.out.println(completion.getJson());
    }
}