package com.online.travel.model.response;

public class Offers {
    private String offerID;

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

    @Override
    public String toString() {
        return "Offers{" +
                "offerID='" + offerID + '\'' +
                '}';
    }
}
