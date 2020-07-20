package com.online.travel.model.referencedata;

public enum PassengerType {
    ADT("Adult"),
    CHD("Child"),
    SRC("Senior citizen");

    private final String description;

    public String getDescription() {
        return this.description;
    }

    private PassengerType(String description) {
        this.description = description;
    }
}
