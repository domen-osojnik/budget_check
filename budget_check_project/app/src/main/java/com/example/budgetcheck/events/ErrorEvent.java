package com.example.budgetcheck.events;

public class ErrorEvent {
    int errorCode;
    String name;
    long timeMS;

    public ErrorEvent(int errorCode, String name) {
        this.errorCode = errorCode;
        this.name = name;
        timeMS = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "MyEventError{" +
                "errorCode=" + errorCode +
                ", name='" + name + '\'' +
                ", timeMS=" + timeMS +
                '}';
    }
}
