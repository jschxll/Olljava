package com.schalljan.model.custom;

import junit.framework.TestCase;

public class ModelFileReaderTest extends TestCase {

    public void testGetFileAsString() {
        ModelFileReader reader = new ModelFileReader("C:\\Users\\Jan Schall\\Desktop\\Modelfile");
        System.out.println(reader.getModelFileContent());
    }
}