package com.example.artsell.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Variables {

    public static String url="http://192.168.0.103:3000/";
    public static String url_2="http://192.168.0.103:4000/";
    public static Retrofit initializeRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
