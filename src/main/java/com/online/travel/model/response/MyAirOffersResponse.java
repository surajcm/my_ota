package com.online.travel.model.response;

public class MyAirOffersResponse {
    private String transactionId;
    private String shoppingResponseId;
    private PricedOffer pricedOffer;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final String transactionId) {
        this.transactionId = transactionId;
    }

    public String getShoppingResponseId() {
        return shoppingResponseId;
    }

    public void setShoppingResponseId(final String shoppingResponseId) {
        this.shoppingResponseId = shoppingResponseId;
    }

    public PricedOffer getPricedOffer() {
        return pricedOffer;
    }

    public void setPricedOffer(final PricedOffer pricedOffer) {
        this.pricedOffer = pricedOffer;
    }

    @Override
    public String toString() {
        return "MyAirOffersResponse{" +
                "transactionId='" + transactionId + '\'' +
                ", shoppingResponseId='" + shoppingResponseId + '\'' +
                ", pricedOffer=" + pricedOffer +
                '}';
    }
}
