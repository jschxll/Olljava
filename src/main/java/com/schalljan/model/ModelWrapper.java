package com.schalljan.model;

import java.util.List;

public class ModelWrapper {
    private List<Model> models;

    public List<Model> getModels() {
        return models;
    }

    @Override
    public String toString() {
        return "ModelWrapper{" +
                "models=" + models +
                '}';
    }
}
