package com.schalljan.model.custom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ModelFileReader {
    private final String filePath;

    public ModelFileReader(String filePath) {
        this.filePath = filePath;
    }

    private File getFile() throws FileNotFoundException {
        File modelFile = new File(filePath);
        if (!modelFile.exists() || !modelFile.canRead())
            throw new FileNotFoundException();
        return modelFile;
    }

    private String readModelFile() throws FileNotFoundException {
        File modelFile = getFile();
        Scanner reader = new Scanner(modelFile);
        StringBuilder builder = new StringBuilder();
        while (reader.hasNextLine()) {
            builder.append(reader.nextLine());
            if (reader.hasNextLine()) {
                builder.append("\\n");
            }
        }
        reader.close();

        String modelFileStr = builder.toString();
        return modelFileStr;
    }

    public String getModelFileContent() {
        try {
            return readModelFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
