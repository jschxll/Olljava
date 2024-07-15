package com.schalljan.utils.stream;

import com.schalljan.model.completion.generate.OllamaCompletion;
import com.schalljan.model.completion.generate.OllamaResponse;
import com.schalljan.utils.Ollama;
import com.schalljan.utils.request.PayloadBuilder;
import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.URL;

public class StreamHandlerTest extends TestCase {

    public void testExecute() {
        Ollama host = new Ollama("http://localhost:11434");
        OllamaCompletion completion = new OllamaCompletion.Builder(host)
                .setModel("phi3")
                .setPrompt("Who was Albert Einstein?")
                .setStreaming(false)
                .build();
        OllamaResponse response = completion.execute().getResponse();
        System.out.println(response.getResponse());
    }
}