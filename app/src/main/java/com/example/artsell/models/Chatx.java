package com.example.artsell.models;

public class Chatx {
    private String user_id;
    private String name;
    private int profilePicture;
    private String message;

    public Chatx(String user_id, String name, int profilePicture, String message) {
        this.user_id = user_id;
        this.name = name;
        this.profilePicture = profilePicture;
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public int getProfilePicture() {
        return profilePicture;
    }

    public String getMessage() {
        return message;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePicture(int profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
