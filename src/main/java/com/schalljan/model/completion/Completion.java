package com.schalljan.model.completion;

import com.schalljan.utils.Duration;
import com.schalljan.utils.Ollama;
import com.schalljan.utils.request.PayloadBuilder;
import com.schalljan.utils.stream.JsonStreamHandler;

public abstract class Completion {
    public abstract Completion execute();
    public abstract Response getResponse();
    public abstract String getJson();

    // Builder class to build the JSON for a Completion
    public static abstract class Builder<B extends Builder<B>> {
        private final Ollama host;
        private final PayloadBuilder payloadBuilder;
        private JsonStreamHandler handler;

        public Builder(Ollama host) {
            this.host = host;
            this.payloadBuilder = new PayloadBuilder();
        }

        public Builder(Ollama host, JsonStreamHandler handler) {
            this.host = host;
            this.handler = handler;
            this.payloadBuilder = new PayloadBuilder();
        }

        public B setFormat(String format) {
            payloadBuilder.setFormat(format);
            return self();
        }

        public B setStreaming(boolean streaming) {
            payloadBuilder.setStream(streaming);
            return self();
        }

        public B setKeepAliveDuration(Duration duration) {
            payloadBuilder.setKeepAliveDuration(duration);
            return self();
        }

        public B setMiroStat(int miroStat) {
            payloadBuilder.setMiroStat(miroStat);
            return self();
        }

        public B setMiroStatEta(float miroStatEta) {
            payloadBuilder.setMiroStatEta(miroStatEta);
            return self();
        }

        public B setMiroStatTau(float miroStatTau) {
            payloadBuilder.setMiroStatTau(miroStatTau);
            return self();
        }

        public B setNumCTX(int numCTX) {
            payloadBuilder.setNumCTX(numCTX);
            return self();
        }

        public B setRepeatLastN(int repeatLastN) {
            payloadBuilder.setRepeatLastN(repeatLastN);
            return self();
        }

        public B setRepeatPenalty(float repeatPenalty) {
            payloadBuilder.setRepeatPenalty(repeatPenalty);
            return self();
        }

        public B setTemperature(float temperature) {
            payloadBuilder.setTemperature(temperature);
            return self();
        }

        public B setSeed(int seed) {
            payloadBuilder.setSeed(seed);
            return self();
        }

        public B setStop(String stop) {
            payloadBuilder.setStop(stop);
            return self();
        }

        public B setTfsZ(float tfsz) {
            payloadBuilder.setTfsZ(tfsz);
            return self();
        }

        public B setNumPredict(int numPredict) {
            payloadBuilder.setNumPredict(numPredict);
            return self();
        }

        public B setTopK(int topK) {
            payloadBuilder.setTopK(topK);
            return self();
        }

        public B setTopP(int topP) {
            payloadBuilder.setTopP(topP);
            return self();
        }

        public Ollama getHost() {
            return host;
        }

        public PayloadBuilder getPayloadBuilder() {
            return payloadBuilder;
        }

        public JsonStreamHandler getHandler() {
            return handler;
        }

        public void setHandler(JsonStreamHandler handler) {
            this.handler = handler;
        }

        // Return a Builder class which extends the Completion.Builder class, to make sure
        // the extended class has the same methods as those here defined
        protected abstract B self();

        public abstract Completion build();
    }
}