package com.schalljan.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/** Encodes images to base64, that can be processed by multimodal LLMs like Llava */
public class ImageConverter {
    public static List<String> convert(List<File> imgs) throws IOException {
        ArrayList<String> convertedImgs = new ArrayList<>();
        for (File img : imgs) {
            if (!img.exists())
                throw new FileNotFoundException();
            FileInputStream stream = new FileInputStream(img);
            byte[] imgData = new byte[(int) img.length()];
            stream.read(imgData);
            stream.close();
            convertedImgs.add(Base64.getEncoder().encodeToString(imgData));
        }
        // Make it a JSON Array item
        return convertedImgs;
    }
}