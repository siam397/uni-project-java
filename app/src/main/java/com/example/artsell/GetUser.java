package com.example.artsell;
import com.google.gson.annotations.SerializedName;
public class GetUser {
    private String username;
    private String email;
    private String _id;
    @SerializedName("body")


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String get_Id() {
        return _id;
    }


}
