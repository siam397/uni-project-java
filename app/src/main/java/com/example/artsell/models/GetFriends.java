package com.example.artsell.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetFriends {
    private final List<Profile>friends=new ArrayList<>();

    public List<Profile> getFriends() {
        return friends;
    }
}
