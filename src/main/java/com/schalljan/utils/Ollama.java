package com.schalljan.utils;

import com.schalljan.model.Model;
import com.schalljan.model.actions.OllamaAction;
import com.schalljan.utils.request.JsonBuilder;
import com.schalljan.utils.request.PayloadBuilder;
import com.schalljan.utils.stream.JsonStreamHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Ollama {
    private URL oURL;

    /**
     * @param url URL to the Ollama API
     */
    public Ollama(String url) {
        try {
            this.oURL = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URL getURL() {
        return oURL;
    }

    public void setURL(URL oURL) {
        this.oURL = oURL;
    }

    public boolean isReachable() {
        try {
            HttpURLConnection conn = (HttpURLConnection) oURL.openConnection();
            conn.connect();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Model> getModels() throws IOException {
        String json = OllamaAction.SHOW_MODELS(this);
        JsonBuilder builder = new JsonBuilder();
        return builder.parseToModel(json).getModels();
    }

    public List<Model> getRunningModels() throws IOException {
        String json = OllamaAction.SHOW_RUNNING_MODELS(this);
        JsonBuilder builder = new JsonBuilder();
        return builder.parseToModel(json).getModels();
    }

    public void copyModel(String modelname, String copiedModelName) throws IOException {
        PayloadBuilder builder = new PayloadBuilder()
                .setSource(modelname)
                .setDestinationName(copiedModelName);
        OllamaAction.COPY_MODEL(this, builder.build().toJson());
    }

    public String pullModel(String modelName, JsonStreamHandler handler) throws IOException {
        PayloadBuilder builder = new PayloadBuilder().setName(modelName);
        return OllamaAction.PULL_MODEL(this, builder.build().toJson(), handler);
    }

    public String pullModel(String modelName) throws IOException {
        PayloadBuilder builder = new PayloadBuilder()
                .setName(modelName)
                .setStream(false);
        return OllamaAction.PULL_MODEL(this, builder.build().toJson(), null);
    }

    public String pushModel(String name, JsonStreamHandler handler) throws IOException {
        PayloadBuilder builder = new PayloadBuilder().setName(name);
        return OllamaAction.PUSH_MODEL(this, builder.build().toJson(), handler);
    }

    public String pushModel(String name) throws IOException {
        PayloadBuilder builder = new PayloadBuilder()
                .setName(name)
                .setStream(false);
        return OllamaAction.PUSH_MODEL(this, builder.build().toJson(), null);
    }

    public List<Float> generateEmbeddings(String json) throws IOException {
        return OllamaAction.GENERATE_EMBEDDINGS(this, json);
    }

    public void deleteModel(String modelToDelete) throws IOException {
        PayloadBuilder builder = new PayloadBuilder().setName(modelToDelete);
        OllamaAction.DELETE_MODEL(this, builder.build().toJson());
    }

    public String createModel(String modelName,  String modelFileContent) throws IOException {
        PayloadBuilder builder = new PayloadBuilder()
                .setName(modelName)
                .setModelFile(modelFileContent)
                .setStream(false);
        return OllamaAction.CREATE_MODEL(this, builder.build().toJson(), null);
    }

    public String createModel(String modelName,  String modelFileContent, JsonStreamHandler handler) throws IOException {
        PayloadBuilder builder = new PayloadBuilder()
                .setName(modelName)
                .setModelFile(modelFileContent);
        return OllamaAction.CREATE_MODEL(this, builder.build().toJson(), handler);
    }
}
