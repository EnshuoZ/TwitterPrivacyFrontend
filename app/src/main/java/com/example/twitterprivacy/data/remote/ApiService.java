package com.example.twitterprivacy.data.remote;

import com.example.twitterprivacy.models.AnalyzeTextInput;
import com.example.twitterprivacy.models.LocationResponse;
import com.example.twitterprivacy.models.Pojo;
import com.example.twitterprivacy.models.RemoveDictionaryWordsInput;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("analyzeText")
    Call<String> analyzeText(@Body AnalyzeTextInput analyzeTextInput);

    @POST("findDictionaryWords")
    Call<ArrayList<Object>> removeDictionaryWords(@Body RemoveDictionaryWordsInput removeDictionaryWordsInput);
    @POST("findmention")
    Call<ArrayList<ArrayList<String>>> findMention(@Body Pojo InputText);
    @POST("findhastag")
    Call<ArrayList<Object>> findHastag(@Body Pojo InputText);

    @POST("findsentiment")
    Call<String> findSentiment(@Body Pojo InputText);
    @POST("findlocation")
    Call<LocationResponse> findLocation(@Body Pojo InputText);
}