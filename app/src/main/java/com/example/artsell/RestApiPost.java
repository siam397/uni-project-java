package com.example.artsell;

import com.example.artsell.models.FriendID;
import com.example.artsell.models.GetFriends;
import com.example.artsell.models.GetUser;
import com.example.artsell.models.LoginUser;
import com.example.artsell.models.People;
import com.example.artsell.models.Profile;
import com.example.artsell.models.ProfilePicture;
import com.example.artsell.models.ResponseBody;
import com.example.artsell.models.SignUser;
import com.example.artsell.models.User;
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
    @POST("suggestedPeople")
    Call<People>getEveryone(@Body UserID id);
    @POST("getRandomUser")
    Call<User>getUser(@Body FriendID friendID);
    @POST("/getUsers")
    Call<People>getUsers(@Body UserID userID);
    @POST("sendRequest")
    Call<ResponseBody>sendRequest(@Body FriendID friendID);
    @POST("addFriend")
    Call<ResponseBody>addFriend(@Body FriendID friendID);
    @POST("removeFriend")
    Call<ResponseBody>removeFriend(@Body FriendID friendID);
    @POST("removeRequest")
    Call<ResponseBody>removeRequest(@Body FriendID friendID);
    @POST("getRequests")
    Call<List<Profile>>getRequests(@Body UserID userID);
    @POST("updateProfilePicture")
    Call<ResponseBody>changeDP(@Body ProfilePicture profilePicture);
}
