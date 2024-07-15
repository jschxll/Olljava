package com.schalljan.model.actions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schalljan.model.Embeddings;
import com.schalljan.utils.stream.JsonStreamHandler;
import com.schalljan.utils.Ollama;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OllamaAction {
    private static final String LOCAL_MODELS_ENDPOINT = "/api/tags";
    private static final String COPY_MODEL_ENDPOINT = "/api/copy";
    private static final String PULL_MODEL_ENDPOINT = "/api/pull";
    private static final String DELETE_MODEL_ENDPOINT = "/api/delete";
    private static final String PUSH_MODEL_ENDPOINT = "/api/push";
    private static final String GENERATE_EMBEDDINGS_ENDPOINT = "/api/embeddings";
    private static final String RUNNING_MODELS_ENDPOINT = "/api/ps";
    private static final String CREATE_MODEL_ENDPOINT = "/api/create";
    private static final String COMPLETION_ENDPOINT = "/api/generate";
    private static final String CHAT_COMPLETION = "/api/chat";

    public static String SHOW_MODELS(Ollama host) throws IOException {
        return getRequest(host, LOCAL_MODELS_ENDPOINT);
    }

    public static String SHOW_RUNNING_MODELS(Ollama host) throws IOException {
        return getRequest(host, RUNNING_MODELS_ENDPOINT);
    }

    public static void COPY_MODEL(Ollama host, String json) throws IOException {
        HttpURLConnection conn = postRequest(host, json, COPY_MODEL_ENDPOINT);
        conn.disconnect();
    }

    // If the sent json contains json=true or doesn't contain anything which deny streaming, the response will be handled as stream
    public static boolean isStream(String json, ObjectMapper mapper) throws JsonProcessingException {
        boolean isStream = false;
        JsonNode node = mapper.readTree(json);
        if (node.has("stream"))
            isStream = node.get("stream").asBoolean();
        else if (!node.has("stream"))
            isStream = true;
        return isStream;
    }

    public static String PULL_MODEL(Ollama host, String json, JsonStreamHandler handler) throws IOException {
        HttpURLConnection connection = postRequest(host, json, PULL_MODEL_ENDPOINT);
        return readResponse(connection, json, handler);
    }

    public static String PUSH_MODEL(Ollama host, String json, JsonStreamHandler handler) throws IOException {
        HttpURLConnection conn = postRequest(host, json, PUSH_MODEL_ENDPOINT);
        return readResponse(conn, json, handler);
    }

    public static void DELETE_MODEL(Ollama host, String json) throws IOException {
        URL url = new URL(host.getURL() + DELETE_MODEL_ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setDoOutput(true);
        byte[] data = json.getBytes(StandardCharsets.UTF_8);
        int len = data.length;
        conn.setFixedLengthStreamingMode(len);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.connect();
        try (OutputStream os = conn.getOutputStream()) {
            os.write(data);
        }
        conn.disconnect();
    }

    public static List<Float> GENERATE_EMBEDDINGS(Ollama host, String json) throws IOException {
        HttpURLConnection conn = postRequest(host, json, GENERATE_EMBEDDINGS_ENDPOINT);
        String response = readResponse(conn, json, null);
        ObjectMapper mapper = new ObjectMapper();
        Embeddings embeddings = mapper.readValue(response, Embeddings.class);
        return embeddings.getVector();
    }

    public static String CREATE_MODEL(Ollama host, String json, JsonStreamHandler handler) throws IOException {
        HttpURLConnection conn = postRequest(host, json, CREATE_MODEL_ENDPOINT);
        return readResponse(conn, json, handler);
    }

    public static String COMPLETION(Ollama host, String json, JsonStreamHandler handler) throws IOException {
        HttpURLConnection conn = postRequest(host, json, COMPLETION_ENDPOINT);
        return readResponse(conn, json, handler);
    }

    public static String CHAT_COMPLETION(Ollama host, String json, JsonStreamHandler handler) throws IOException {
        HttpURLConnection conn = postRequest(host, json, CHAT_COMPLETION);
        return readResponse(conn, json, handler);
    }

    private static String readResponse(HttpURLConnection conn, String json, JsonStreamHandler handler) throws IOException {
        String response;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder respBuilder = new StringBuilder();
            String line;
            ObjectMapper mapper = new ObjectMapper();
            boolean isStream = isStream(json, mapper);
            while ((line = reader.readLine()) != null) {
                respBuilder.append(line);
                if (isStream && handler != null) {
                    JsonNode node = mapper.readTree(line);
                    handler.accept(node.toString());
                }
            }
            response = respBuilder.toString();
        }
        conn.disconnect();
        return response;
    }

    private static HttpURLConnection postRequest(Ollama host, String json, String endpoint) throws IOException {
        URL url = new URL(host.getURL() + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        byte[] data = json.getBytes(StandardCharsets.UTF_8);
        int len = data.length;
        conn.setFixedLengthStreamingMode(len);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.connect();
        try (OutputStream os = conn.getOutputStream()) {
            os.write(data);
        }
        return conn;
    }

    private static String getRequest(Ollama host, String endpoint) throws IOException {
        URL url = new URL(host.getURL() + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        conn.disconnect();
        return builder.toString();
    }
}
