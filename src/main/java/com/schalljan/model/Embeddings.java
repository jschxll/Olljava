package com.schalljan.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Embeddings {
    @JsonProperty("embedding")
    private List<Float> vector;

    public List<Float> getVector() {
        return vector;
    }
}
