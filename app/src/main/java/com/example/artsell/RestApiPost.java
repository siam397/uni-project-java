package com.example.artsell;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestApiPost {
    @POST("login")
    Call<GetUser> loginUser(@Body LoginUser loginUser);
    @POST("signup")
    Call<GetUser> signupUser(@Body SignUser user);
    @POST("getProfileInfo")
    Call<GetProfile> getProfileInfo(@Body UserID userID);
}
