package com.schalljan.utils.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schalljan.model.ModelWrapper;
import com.schalljan.model.completion.chat.Message;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonBuilder {
    private Payload args;

    public JsonBuilder(Payload args) {
        this.args = args;
    }

    public JsonBuilder() {
    }

    private String parseToJson(Payload args) {
        StringBuilder json = new StringBuilder("{\n");
        List<String> keys = new ArrayList<>(args.getJsonMap().keySet());
        args.getJsonMap().keySet().forEach(arg -> {
            Object currVal = args.getJsonMap().get(arg);

            if (currVal instanceof Map) {
                List<String> opKeys = (List<String>) new ArrayList<>(((Map<?, ?>) currVal).keySet());
                json.append("\"").append("options").append("\"").append(":").append("{");
                ((Map<?, ?>) currVal).keySet().forEach(opArg -> {
                    Object opCurrVal = ((Map<?, ?>) currVal).get(opArg);
                    json.append("\n\t").append("\"").append(opArg).append("\"").append(":")
                            .append(opCurrVal instanceof String ? "\"" + opCurrVal + "\"" : opCurrVal);

                    if (opKeys.indexOf(opArg) < opKeys.size() - 1) {
                        json.append(",");
                    }
                });
                json.append("\n\t}");
            } else if (currVal instanceof List && ((List<?>) currVal).get(0) instanceof String
                    && isBase64Encoded((String) ((List<?>) currVal).get(0))) {
                json.append("\"images\":");
                String convList = imagesToJsonList((List<String>) currVal);
                json.append(convList);
            } else if (currVal instanceof List && ((List<?>) currVal).get(0) instanceof Message) {
                handleMessageList((List<?>) currVal, json);
            } else {
                json.append("\"").append(arg).append("\"").append(":")
                        .append(currVal instanceof String ? "\"" + currVal + "\"" : currVal);
            }

            if (keys.indexOf(arg) < keys.size() - 1) {
                json.append(",\n");
            }
        });
        json.append("\n}");
        return json.toString();
    }

    private boolean isBase64Encoded(String str) {
        try {
            return str.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
        } catch (ClassCastException e) {
            return false;
        }
    }

    private void handleMessageList(List<?> list, StringBuilder strBuilder) {
        strBuilder.append("\"messages\":[");
        list.forEach(item -> {
            strBuilder.append(handleSingleMessage((Message) item));
            // If the item isn't the last item in the list, a comma separate it from the next item
            if (list.indexOf(item) < list.size() - 1)
                strBuilder.append(",\n");
        });
        strBuilder.append("]");
    }

//    public void handleListTest(List<?> list, StringBuilder strBuilder) {
//        handleMessageList(list, strBuilder);
//    }

    // Convert a Message item to a Json element
    private String handleSingleMessage(Message message) {
        String json;
        if (message.hasImages())
            json = "{\n\"role\":" + "\"" + message.getRole() + "\"" + ",\n\"content\":" + "\"" + message.getContent() + "\"" +
                    ",\n\"images\":" + imagesToJsonList(message.getImages()) + "\n}";
        else
            json = "{\n\"role\":" + "\"" + message.getRole() + "\"" + ",\n\"content\":" + "\"" + message.getContent() + "\"" + "\n}";
        return json;
    }

    private String imagesToJsonList(List<String> images) {
        StringBuilder imgList = new StringBuilder("[");
        for (String image : images) {
            imgList.append("\"").append(image).append("\"");
            if (images.indexOf(image) < images.size() - 1)
                imgList.append(",");
        }
        imgList.append("]");
        return imgList.toString();
    }

    public ModelWrapper parseToModel(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, ModelWrapper.class);
    }

    public String getJsonAsString() {
        return parseToJson(args);
    }

    public void setArgs(Payload args) {
        this.args = args;
    }
}
