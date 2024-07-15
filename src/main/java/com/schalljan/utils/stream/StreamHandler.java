package com.schalljan.utils.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StreamHandler implements JsonStreamHandler {
    public static final String VERBOSE = "verbose";
    public static final String RESPONSE_ONLY = "response";
    public static final String DATA_ONLY = "data";
    private String mode = VERBOSE;
    private final ObjectMapper mapper = new ObjectMapper();

    public StreamHandler(String mode) {
        this.mode = mode;
    }
    public StreamHandler() {}

    @Override
    public void accept(String s) {
        switch (mode) {
            case VERBOSE:
                System.out.println(s);
                break;
            case RESPONSE_ONLY:
                responseOnly(s);
                break;
            default:
                break;
        }
    }

    private void responseOnly(String jsonString) {
        try {
            JsonNode json = mapper.readTree(jsonString);
            String response = json.get("response").asText().replace("\n", "");
            System.out.print(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}