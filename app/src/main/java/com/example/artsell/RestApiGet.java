package com.example.artsell;

import com.example.artsell.models.GetUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface RestApiGet {
    @GET("getFriends")
    Call<List<GetUser>> getFriends();

}
