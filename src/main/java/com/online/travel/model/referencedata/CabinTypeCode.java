package com.online.travel.model.referencedata;

import java.util.Arrays;
import java.util.Optional;

public enum CabinTypeCode {
    P("Premium First"),
    F("First"),
    J("Premium Business"),
    C("Business"),
    M("Economic Standard"),
    S("Premium Economy"),
    Y("Economy");

    private final String description;

    public String getDescription() {
        return this.description;
    }

    private CabinTypeCode(String description) {
        this.description = description;
    }
    public static Optional<CabinTypeCode> fromDescription(String description) {
        return Arrays.stream(CabinTypeCode.values())
                .filter(c -> c.description.equalsIgnoreCase(description))
                .findFirst();
    }
}
