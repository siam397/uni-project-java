package com.example.artsell;

import com.google.gson.annotations.SerializedName;

public class LoginUser {
    private String email;
    private String password;
    @SerializedName("body")
    private String token;

    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
