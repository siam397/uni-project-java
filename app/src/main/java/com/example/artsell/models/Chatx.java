package com.example.artsell.models;

public class Chatx {
    private String id;
    private String name;
    private String profilePicture;
    private String message;
    private String toPerson;
    private String fromPersonId;
    public Chatx(){}

    public Chatx(String id, String name, String profilePicture, String message, String toPerson, String fromPersonId) {
        this.id = id;
        this.name = name;
        this.profilePicture = profilePicture;
        this.message = message;
        this.toPerson = toPerson;
        this.fromPersonId = fromPersonId;
    }

    public void setFromPersonId(String fromPersonId) {
        this.fromPersonId = fromPersonId;
    }

    public String getFromPersonId() {
        return fromPersonId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getMessage() {
        return message;
    }

    public String getToPerson() {
        return toPerson;
    }

    public void setToPerson(String toPerson) {
        this.toPerson = toPerson;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
