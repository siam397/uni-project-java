package com.example.artsell.models;

import java.util.List;

public class People {
    private List<User>suggestedPeople;
    private List<User>everyoneList;
    private int numberOfRequests;
    public People(List<User> suggestedPeople, List<User> everyoneList, int numberOfRequests) {
        this.suggestedPeople = suggestedPeople;
        this.everyoneList = everyoneList;
        this.numberOfRequests = numberOfRequests;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(int numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    public List<User> getsuggestedPeople() {
        return suggestedPeople;
    }

    public List<User> geteveryoneList() {
        return everyoneList;
    }

    public void setsuggestedPeople(List<User> suggestedPeople) {
        this.suggestedPeople = suggestedPeople;
    }

    public void seteveryoneList(List<User> everyoneList) {
        this.everyoneList = everyoneList;
    }
}
