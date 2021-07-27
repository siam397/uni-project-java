package com.example.artsell.models;

public class User {
    private String user_id;
    private String username;
    private String bio;
    private String profilePicture;
    private boolean friends;
    private boolean sentFriendRequests;
    private boolean requested;

    public User(String user_id, String username, String bio, String profilePicture, boolean friends, boolean sentFriendRequests, boolean requested) {
        this.user_id = user_id;
        this.username = username;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.friends = friends;
        this.sentFriendRequests = sentFriendRequests;
        this.requested = requested;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public boolean isFriends() {
        return friends;
    }

    public boolean isSentFriendRequests() {
        return sentFriendRequests;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setFriends(boolean friends) {
        this.friends = friends;
    }

    public void setSentFriendRequests(boolean sentFriendRequests) {
        this.sentFriendRequests = sentFriendRequests;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }
}
