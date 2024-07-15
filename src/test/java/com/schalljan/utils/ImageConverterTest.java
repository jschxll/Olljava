package com.schalljan.utils;

import com.schalljan.model.completion.generate.OllamaCompletion;
import com.schalljan.model.completion.generate.OllamaResponse;
import com.schalljan.utils.request.PayloadBuilder;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ImageConverterTest extends TestCase {

    public void testConvert() throws IOException {
        List<String> img = ImageConverter.convert(List.of(new File("C:\\Users\\Jan Schall\\Pictures\\Wallpaper.jpg"),
                new File("C:\\Users\\Jan Schall\\Pictures\\Screenshot 2023-08-24 140354.png")));
        PayloadBuilder builder = new PayloadBuilder()
                .setImages(List.of(new File("C:\\Users\\Jan Schall\\Pictures\\Wallpaper.jpg"),
                        new File("C:\\Users\\Jan Schall\\Pictures\\Screenshot 2023-08-24 140354.png")));

        System.out.println(builder.build().toJson());
    }

//    public void testConvertRequest() throws IOException {
//        Ollama host = new Ollama(new URL("http://localhost:11434"));
//        PayloadBuilder builder = new PayloadBuilder()
//                .setModel("llava")
//                .setPrompt("What do you see on those images?")
//                .setStream(false)
//                .setImages(List.of(new File("C:\\Users\\Jan Schall\\Pictures\\Wallpaper.jpg"),
//                        new File("C:\\Users\\Jan Schall\\Pictures\\Screenshot 2023-08-24 140354.png")));
//        System.out.println(builder.build().toJson());
//        OllamaCompletion completion = new OllamaCompletion(builder.build().toJson(), host);
//        OllamaResponse response = completion.execute().getResponse();
//        System.out.println(response.getResponse());
//    }
}