package com.example.artsell.models;

import java.util.ArrayList;
import java.util.List;

public class People {
    List<User>suggestedPeople;
    List<User>everyoneList;

    public People(List<User> suggestedPeople, List<User> everyoneList) {
        this.suggestedPeople = suggestedPeople;
        this.everyoneList = everyoneList;
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
