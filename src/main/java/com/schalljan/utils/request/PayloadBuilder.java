package com.schalljan.utils.request;

import com.schalljan.model.completion.chat.Message;
import com.schalljan.utils.Duration;
import com.schalljan.utils.ImageConverter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public class PayloadBuilder {
    private Payload payload;
    private LinkedHashMap<String, Object> options;

    public PayloadBuilder() {
        this.payload = new Payload(new LinkedHashMap<>());
        this.options = new LinkedHashMap<>();
    }

    public PayloadBuilder setModel(String model) {
        payload.getJsonMap().put("model", model);
        return this;
    }

    public PayloadBuilder setName(String name) {
        payload.getJsonMap().put("name", name);
        return this;
    }

    public PayloadBuilder setPrompt(String prompt) {
        payload.getJsonMap().put("prompt", prompt);
        return this;
    }

    public PayloadBuilder setImages(List<File> imgs) {
        List<String> bas64Imgs;
        try {
            bas64Imgs = ImageConverter.convert(imgs);
        } catch (IOException e) {
            bas64Imgs = null;
        }
        payload.getJsonMap().put("images", bas64Imgs);
        return this;
    }

    public PayloadBuilder setFormat(String format) {
        payload.getJsonMap().put("format", format);
        return this;
    }

    public PayloadBuilder setMiroStat(int miroStat) {
        options.put("mirostat", miroStat);
        return this;
    }

    public PayloadBuilder setMiroStatEta(float miroStatEta) {
        options.put("mirostat_eta", miroStatEta);
        return this;
    }

    public PayloadBuilder setMiroStatTau(float miroStatTau) {
        options.put("mirostat_tau", miroStatTau);
        return this;
    }

    public PayloadBuilder setNumCTX(int numCTX)  {
        options.put("num_ctx", numCTX);
        return this;
    }

    public PayloadBuilder setRepeatLastN(int repeatLastN) {
        options.put("repeat_last_n", repeatLastN);
        return this;
    }

    public PayloadBuilder setRepeatPenalty(float repeatPenalty) {
        options.put("repeat_penalty", repeatPenalty);
        return this;
    }

    public PayloadBuilder setTemperature(float temperature) {
        options.put("temperature", temperature);
        return this;
    }

    public PayloadBuilder setSeed(int seed) {
        options.put("seed", seed);
        return this;
    }

    public PayloadBuilder setStop(String stop) {
        options.put("stop", stop);
        return this;
    }

    public PayloadBuilder setTfsZ(float tfsz) {
        options.put("tfs_z", tfsz);
        return this;
    }

    public PayloadBuilder setNumPredict(int numPredict) {
        payload.getJsonMap().put("num_predict", numPredict);
        return this;
    }

    public PayloadBuilder setTopK(int topK) {
        options.put("top_k", topK);
        return this;
    }

    public PayloadBuilder setTopP(int topP) {
        options.put("top_p", topP);
        return this;
    }

    public PayloadBuilder setSystemMessage(String message) {
        payload.getJsonMap().put("system", message);
        return this;
    }

    public PayloadBuilder setStream(boolean stream) {
        payload.getJsonMap().put("stream", stream);
        return this;
    }

    public PayloadBuilder setPromptTemplate(String template) {
        payload.getJsonMap().put("template", template);
        return this;
    }

    public PayloadBuilder setContext(List<Integer> context) {
        payload.getJsonMap().put("context", context.toString());
        return this;
    }

    public PayloadBuilder setRaw(boolean raw) {
        payload.getJsonMap().put("raw", raw);
        return this;
    }

    public PayloadBuilder setKeepAliveDuration(Duration duration) {
        payload.getJsonMap().put("keep_alive", duration.getTime());
        return this;
    }

    public PayloadBuilder setSource(String source) {
        payload.getJsonMap().put("source", source);
        return this;
    }

    public PayloadBuilder setDestinationName(String destinationName) {
        payload.getJsonMap().put("destination", destinationName);
        return this;
    }

    public PayloadBuilder setModelFile(String modelFileContent) {
        payload.getJsonMap().put("modelfile", modelFileContent);
        return this;
    }

    public PayloadBuilder setChatMessages(List<Message> messages) {
        payload.getJsonMap().put("messages", messages);
        return this;
    }

    public Payload build() {
        if (!options.isEmpty())
            payload.getJsonMap().put("options", options);
        return payload;
    }
}
