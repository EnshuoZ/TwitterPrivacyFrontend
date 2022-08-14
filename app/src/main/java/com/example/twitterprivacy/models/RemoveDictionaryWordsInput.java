package com.example.twitterprivacy.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RemoveDictionaryWordsInput {
    @SerializedName("InputText")
    private String inputText;
    @SerializedName("dictionary")
    private ArrayList<String> dictionary;

    public RemoveDictionaryWordsInput(String inputText, ArrayList<String> dictionary) {
        this.inputText = inputText;
        this.dictionary = dictionary;
    }

    public String getInputText() {
        return inputText;
    }

    public ArrayList<String> getDictionary() {
        return dictionary;
    }
}
