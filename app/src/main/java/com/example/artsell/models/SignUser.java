package com.example.artsell.models;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Body;

public class SignUser {

    private String email;
    private String username;
    private String password;


    public SignUser(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
