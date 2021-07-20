package com.example.artsell.models;

public class Profile {
    private String user_id;
    private String username;
    private String bio;
    private String profilePicture;
    private String lastText;
//    private String[] posts;

    public Profile(){ }

    //C O N S T R U C T O R
    public Profile(String user_id, String username, String bio, String profilePicture, String lastText) {
        this.user_id = user_id;
        this.username = username;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.lastText = lastText;
    }



    //G E T T E R
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

    public String getLastText() {
        return lastText;
    }

//    public String[] getPosts() {
//        return posts;
//    }



    //S E T T E R
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

    public void setLastText(String lastText) {
        this.lastText = lastText;
    }

//    public void setPosts(String[] posts) {
//        this.posts = posts;
//    }
}
