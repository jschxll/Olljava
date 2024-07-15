package com.schalljan.model.completion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private String model;
    @JsonProperty("created_at")
    private OffsetDateTime creationDate;
    @JsonProperty("done")
    private boolean isDone;
    @JsonProperty("total_duration")
    private long totalDuration;
    @JsonProperty("load_duration")
    private long loadDuration;
    @JsonProperty("prompt_eval_count")
    private int promptEvalCount;
    @JsonProperty("prompt_eval_duration")
    private long promptEvalDuration;
    @JsonProperty("eval_count")
    private int evalCount;
    @JsonProperty("eval_duration")
    private long evalDuration;

    public String getModel() {
        return model;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public boolean isDone() {
        return isDone;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public long getLoadDuration() {
        return loadDuration;
    }

    public int getPromptEvalCount() {
        return promptEvalCount;
    }

    public long getPromptEvalDuration() {
        return promptEvalDuration;
    }

    public int getEvalCount() {
        return evalCount;
    }

    public long getEvalDuration() {
        return evalDuration;
    }

    @Override
    public String toString() {
        return "Response{" +
                "model='" + model + '\'' +
                ", creationDate=" + creationDate +
                ", isDone=" + isDone +
                ", totalDuration=" + totalDuration +
                ", loadDuration=" + loadDuration +
                ", promptEvalCount=" + promptEvalCount +
                ", promptEvalDuration=" + promptEvalDuration +
                ", evalCount=" + evalCount +
                ", evalDuration=" + evalDuration +
                '}';
    }
}
