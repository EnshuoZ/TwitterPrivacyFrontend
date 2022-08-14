package com.example.twitterprivacy.data.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class Test implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request =chain.request();
        Response response = chain.proceed(request);
         Log.v("response", "nn" + response);
        return response;
    }

}



