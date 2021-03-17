package com.online.travel.model.response;

import java.util.List;

public class MyAirShoppingResponse {
    private int totalResultsCount;
    private String transactionId;
    private List<Segments> segments;
    private List<Offers> offers;

    public int getTotalResultsCount() {
        return totalResultsCount;
    }

    public void setTotalResultsCount(final int totalResultsCount) {
        this.totalResultsCount = totalResultsCount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final String transactionId) {
        this.transactionId = transactionId;
    }

    public List<Segments> getSegments() {
        return segments;
    }

    public void setSegments(final List<Segments> segments) {
        this.segments = segments;
    }

    public List<Offers> getOffers() {
        return offers;
    }

    public void setOffers(final List<Offers> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "MyAirShoppingResponse{" +
                "totalResultsCount=" + totalResultsCount +
                ", transactionId=" + transactionId +
                ", segments=" + segments +
                ", offers=" + offers +
                '}';
    }
}
