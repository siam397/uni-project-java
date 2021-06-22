package com.example.artsell;

import com.example.artsell.models.GetFriends;
import com.example.artsell.models.GetUser;
import com.example.artsell.models.LoginUser;
import com.example.artsell.models.Profile;
import com.example.artsell.models.SignUser;
import com.example.artsell.models.UserID;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestApiPost {
    @POST("login")
    Call<GetUser> loginUser(@Body LoginUser loginUser);
    @POST("signup")
    Call<GetUser> signupUser(@Body SignUser user);
    @POST("getProfileInfo")
    Call<Profile> getProfileInfo(@Body UserID userID);
    @POST("getFriends")
    Call<GetFriends> getFriends(@Body UserID userID);
}
