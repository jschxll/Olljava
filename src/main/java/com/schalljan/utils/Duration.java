package com.schalljan.utils;

public class Duration {
    private final int time;
    public Duration(int time) {
        this.time = time;
    }

    public String getTime() {
        return time + "m";
    }
}
