package com.schalljan.model.completion.chat;

import com.schalljan.utils.ImageConverter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Message {
    private String role;
    private String content;
    private List<File> images;
    private List<String> convertedImages;

    public Message(String role, String content, List<File> images) {
        this.role = role;
        this.content = content;
        this.images = images;

        try {
            convertedImages = ImageConverter.convert(images);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public Message() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return convertedImages;
    }

    public void setImages(List<File> imgs) {
        this.images = imgs;
        try {
            convertedImages = ImageConverter.convert(imgs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasImages() {
        return images != null;
    }

    @Override
    public String toString() {
        return "Message{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                ", images=" + images +
                '}';
    }
}
