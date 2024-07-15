package com.schalljan.utils.stream;
import java.util.function.Consumer;

public interface JsonStreamHandler extends Consumer<String> {
    @Override
    void accept(String s);
}
