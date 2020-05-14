package com.example.budgetcheck.events;

public class InfoEvent {
    String name;
    String information;

    public InfoEvent(String name, String information) {
        this.name = name;
        this.information = information;
    }

    @Override
    public String toString() {
        return "MyEventInfo{" +
                "Tag=" + name +
                ", detail='" + information + '\'' +
                '}';
    }
}
