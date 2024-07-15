package com.schalljan.model.completion.generate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schalljan.model.completion.Response;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OllamaResponse extends Response {
    private String response;
    private List<Integer> context;
    @JsonProperty("done_reason")
    private String doneReason;

    public String getResponse() {
        return response;
    }

    public List<Integer> getContext() {
        return context;
    }

    public String getDoneReason() {
        return doneReason;
    }
}
