package com.example.twitterprivacy.models;

public class AnalyzeTextResponse {
    private int isShared;
    private String msg;

    public AnalyzeTextResponse(int isShared, String msg) {
        this.isShared = isShared;
        this.msg = msg;
    }

    public int getShared() {
        return isShared;
    }

    public String getMsg() {
        return msg;
    }
}

