package com.jdvila.datetrivia.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NumberRetrofit {
    private static final String BASE_URL = "http://numbersapi.com/";
    private static Retrofit retrofitInstance;

    public static Retrofit getRetrofitInstance() {
        if(retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }
}
