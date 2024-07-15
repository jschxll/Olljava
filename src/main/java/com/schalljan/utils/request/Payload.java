package com.schalljan.utils.request;

import java.util.Map;

public class Payload {
    private Map<String, Object> jsonMap;

    public Payload(Map<String, Object> jsonMap) {
        this.jsonMap = jsonMap;
    }

    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "jsonMap=" + jsonMap +
                '}';
    }

    public String toJson() {
        JsonBuilder builder = new JsonBuilder(this);
        return builder.getJsonAsString();
    }
}
