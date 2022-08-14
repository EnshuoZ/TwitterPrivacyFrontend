package com.example.twitterprivacy.models;

import com.google.gson.annotations.SerializedName;

public class PojoResponse {
    @SerializedName("detail") private String detail;

    public PojoResponse(String detail) {
        this.detail = detail;
    }
}
