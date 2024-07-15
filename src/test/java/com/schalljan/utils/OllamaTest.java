package com.schalljan.utils;

import com.schalljan.model.Model;
import com.schalljan.model.custom.ModelFileReader;
import com.schalljan.utils.request.PayloadBuilder;
import com.schalljan.utils.stream.StreamHandler;
import junit.framework.TestCase;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class OllamaTest extends TestCase {

    public void testGetModels() throws IOException {
        Ollama host = new Ollama("http://localhost:11434");
        List<Model> models = host.getModels();
        assertEquals("llava:latest", models.get(0).getName());
    }

    public void testGetRunningModels() throws IOException {
        Ollama host = new Ollama("http://localhost:11434");
        List<Model> models = host.getRunningModels();
        System.out.println(models);
    }

    public void testPullModel() throws IOException {
        Ollama host = new Ollama("http://localhost:11434");
        if (host.isReachable()) {
            host.pullModel("llama3:latest", new StreamHandler());
        }
    }

    public void testCopyModel() throws IOException {
        Ollama host = new Ollama("http://localhost:11434");
        host.copyModel("phi3", "phi3_copy");
    }

    public void testDeleteModel() throws IOException {
        Ollama host = new Ollama("http://localhost:11434");
        host.deleteModel("Mario");
    }

    public void testGenerateEmbeddings() throws IOException {
        Ollama host = new Ollama("http://localhost:11434");
        PayloadBuilder builder = new PayloadBuilder()
                .setModel("phi3")
                .setPrompt("The sky is blue because ...");
        List<Float> embeddings = host.generateEmbeddings(builder.build().toJson());
        System.out.println(embeddings);
    }

    public void testCreateModel() throws IOException {
        Ollama host = new Ollama("http://localhost:11434");
        ModelFileReader reader = new ModelFileReader("C:\\Users\\Jan Schall\\Desktop\\Modelfile");
        host.createModel("Mario", reader.getModelFileContent(), new StreamHandler());
    }
}