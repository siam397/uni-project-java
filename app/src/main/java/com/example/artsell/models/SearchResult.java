package com.example.artsell.models;

public class SearchResult {
    private String user_id;
    private String username;
    private int profilePicture;
    private String bio;

    public SearchResult(String user_id, String username, int profilePicture, String bio) {
        this.user_id = user_id;
        this.username = username;
        this.profilePicture = profilePicture;
        this.bio = bio;
    }


    // GETTER
    public String getUser_id() {
        return user_id;
    }
    public String getUsername() {
        return username;
    }
    public int getProfilePicture() {
        return profilePicture;
    }
    public String getBio() {
        return bio;
    }


    // SETTER
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setProfilePicture(int profilePicture) {
        this.profilePicture = profilePicture;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
}
