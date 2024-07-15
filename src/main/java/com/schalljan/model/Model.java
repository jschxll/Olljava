package com.schalljan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Model {
    private String name, model, size, digest;
    @JsonProperty("modified_at")
    private String modificationDate;
    private Details details;
    @JsonProperty("expires_at")
    private String expireDate;
    @JsonProperty("size_vram")
    private long vramSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public long getVramSize() {
        return vramSize;
    }

    public void setVramSize(long vramSize) {
        this.vramSize = vramSize;
    }

    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", size='" + size + '\'' +
                ", digest='" + digest + '\'' +
                ", modificationDate='" + modificationDate + '\'' +
                ", details=" + details +
                ", expireDate='" + expireDate + '\'' +
                ", vramSize=" + vramSize +
                '}';
    }

    public static class Details {
        private String format;
        @JsonProperty("parent_model")
        private String parentModel;
        private String family;
        @JsonProperty("parameter_size")
        private String parameterSize;
        @JsonProperty("quantization_level")
        private String quantizationLevel;
        private List<String> families;

        public String getFormat() {
            return format;
        }

        public String getParentModel() {
            return parentModel;
        }

        public String getFamily() {
            return family;
        }

        public String getParameterSize() {
            return parameterSize;
        }

        public String getQuantizationLevel() {
            return quantizationLevel;
        }

        public List<String> getFamilies() {
            return families;
        }

        @Override
        public String toString() {
            return "ModelDetails{" +
                    "format='" + format + '\'' +
                    ", family='" + family + '\'' +
                    ", families='" + families + '\'' +
                    ", parameter_size='" + parameterSize + '\'' +
                    ", quantization_level='" + quantizationLevel + '\'' +
                    '}';
        }
    }
}
