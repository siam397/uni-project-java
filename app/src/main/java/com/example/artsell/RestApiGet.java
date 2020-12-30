package com.example.artsell;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface RestApiGet {
    @GET("getUsers")
    Call<List<GetUser>> getPosts();

}
