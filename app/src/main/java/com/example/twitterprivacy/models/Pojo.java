package com.example.twitterprivacy.models;

import com.google.gson.annotations.SerializedName;

public class Pojo {
    @SerializedName("InputText") private String inputText;

    public Pojo(String inputText) {
        this.inputText = inputText;
    }
}

