package com.example.twitterprivacy.data.remote;


import com.example.twitterprivacy.models.AnalyzeTextInput;
import com.example.twitterprivacy.models.LocationResponse;
import com.example.twitterprivacy.models.Pojo;
import com.example.twitterprivacy.models.RemoveDictionaryWordsInput;

import java.util.ArrayList;

import retrofit2.Call;

public interface ApiHelper {
    Call<String>  analyzeText(AnalyzeTextInput analyzeTextInput);
    Call<ArrayList<Object>> removeDictionaryWords(RemoveDictionaryWordsInput removeDictionaryWordsInput);
    Call<ArrayList<ArrayList<String>>> findMention(Pojo InputText);
    Call<ArrayList<Object>> findHastag(Pojo InputText);
    Call<String> findSentiment( Pojo InputText);
    Call<LocationResponse> findLocation(Pojo InputText);



}
