package com.example.artsell.models;

public class FriendID {
    public String firstId;
    public String secondId;

    public FriendID(String firstId, String secondId) {
        this.firstId = firstId;
        this.secondId = secondId;
    }

    public String getFirstId() {
        return firstId;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }
}
