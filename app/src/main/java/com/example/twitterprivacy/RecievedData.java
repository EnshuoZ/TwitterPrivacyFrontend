package com.example.twitterprivacy;

import java.util.List;

public class RecievedData {

    List<tweetData> data;

    public RecievedData(List<tweetData> data) {
        this.data = data;
    }

    public List<tweetData> getData() {
        return data;
    }

    public void setData(List<tweetData> data) {
        this.data = data;
    }
}
