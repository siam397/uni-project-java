package com.example.artsell.models;

public class ProfilePicture {
    private String ID;
    private String DP;

    public ProfilePicture(String ID, String DP) {
        this.ID = ID;
        this.DP = DP;
    }

    public String getID() {
        return ID;
    }

    public String getDP() {
        return DP;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDP(String DP) {
        this.DP = DP;
    }
}
