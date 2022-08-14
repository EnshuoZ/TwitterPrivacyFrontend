package com.example.twitterprivacy.data.remote;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    static Retrofit getInstance() {
        return new Retrofit.Builder().baseUrl("https://twitterprivacy.herokuapp.com/").addConverterFactory(
                GsonConverterFactory.create()).build();
//        return new Retrofit.Builder().client(myHttpClient()).baseUrl("https://twitterprivacy.herokuapp.com/").
//                addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static ApiService getApiService() {
        return getInstance().create(ApiService.class);
    }
    static OkHttpClient myHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .addInterceptor(new Test());
        return builder.build();
    }
}

