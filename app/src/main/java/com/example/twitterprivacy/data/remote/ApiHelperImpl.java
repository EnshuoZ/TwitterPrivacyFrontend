package com.example.twitterprivacy.data.remote;

import com.example.twitterprivacy.models.AnalyzeTextInput;
import com.example.twitterprivacy.models.LocationResponse;
import com.example.twitterprivacy.models.Pojo;
import com.example.twitterprivacy.models.RemoveDictionaryWordsInput;

import java.util.ArrayList;

import retrofit2.Call;

public class ApiHelperImpl implements ApiHelper {
    private ApiService apiService;
    public ApiHelperImpl(ApiService apiService){
        this.apiService = apiService;
    }

    @Override
    public Call<String> analyzeText(AnalyzeTextInput analyzeTextInput) {
        return apiService.analyzeText(analyzeTextInput);
    }

    @Override
    public Call<ArrayList<Object>> removeDictionaryWords(RemoveDictionaryWordsInput removeDictionaryWordsInput) {
        return apiService.removeDictionaryWords(removeDictionaryWordsInput);
    }

    @Override
    public Call<ArrayList<ArrayList<String>>> findMention(Pojo InputText) {
        return apiService.findMention(InputText);
    }

    @Override
    public Call<ArrayList<Object>> findHastag(Pojo InputText) {
        return apiService.findHastag(InputText);
    }

    @Override
    public Call<String> findSentiment(Pojo InputText) {
        return apiService.findSentiment(InputText);
    }

    @Override
    public Call<LocationResponse> findLocation(Pojo InputText) {
        return apiService.findLocation(InputText);
    }
}
