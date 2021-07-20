package com.example.artsell.models;

public class Chatx {
    private String user_id;
    private String username;
    private int profilePicture;
    private String lastText;

    public Chatx(String user_id, String username, int profilePicture, String lastText) {
        this.user_id = user_id;
        this.username = username;
        this.profilePicture = profilePicture;
        this.lastText = lastText;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public int getProfilePicture() {
        return profilePicture;
    }

    public String getLastText() {
        return lastText;
    }
}
