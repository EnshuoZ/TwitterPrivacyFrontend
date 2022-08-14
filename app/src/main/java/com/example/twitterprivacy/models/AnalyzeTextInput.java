package com.example.twitterprivacy.models;

import com.google.gson.annotations.SerializedName;

public class AnalyzeTextInput {
    @SerializedName("InputText")
    private String inputText;
    @SerializedName("username")
    private String userName;

    public AnalyzeTextInput(String inputText, String userName) {
        this.inputText = inputText;
        this.userName = userName;
    }

    public String getInputText() {
        return inputText;
    }

    public String getUserName() {
        return userName;
    }
}
