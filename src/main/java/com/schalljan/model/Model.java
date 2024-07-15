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
        private String format, parent_model, family, parameter_size, quantization_level;
        private List<String> families;

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getParent_model() {
            return parent_model;
        }

        public void setParent_model(String parent_model) {
            this.parent_model = parent_model;
        }

        public String getFamily() {
            return family;
        }

        public void setFamily(String family) {
            this.family = family;
        }

        public List<String> getFamilies() {
            return families;
        }

        public void setFamilies(List<String> families) {
            this.families = families;
        }

        public String getParameter_size() {
            return parameter_size;
        }

        public void setParameter_size(String parameter_size) {
            this.parameter_size = parameter_size;
        }

        public String getQuantization_level() {
            return quantization_level;
        }

        public void setQuantization_level(String quantization_level) {
            this.quantization_level = quantization_level;
        }

        @Override
        public String toString() {
            return "ModelDetails{" +
                    "format='" + format + '\'' +
                    ", family='" + family + '\'' +
                    ", families='" + families + '\'' +
                    ", parameter_size='" + parameter_size + '\'' +
                    ", quantization_level='" + quantization_level + '\'' +
                    '}';
        }
    }
}
