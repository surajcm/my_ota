package com.online.travel.model.response;

import java.math.BigDecimal;

public class PricedOfferItem {
    private String offerItemID;
    private String offerItemTypeCode;
    private BigDecimal totalAmount;
    private String currency;

    public String getOfferItemID() {
        return offerItemID;
    }

    public void setOfferItemID(final String offerItemID) {
        this.offerItemID = offerItemID;
    }

    public String getOfferItemTypeCode() {
        return offerItemTypeCode;
    }

    public void setOfferItemTypeCode(final String offerItemTypeCode) {
        this.offerItemTypeCode = offerItemTypeCode;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(final BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "PricedOfferItem{" +
                "offerItemID='" + offerItemID + '\'' +
                ", offerItemTypeCode='" + offerItemTypeCode + '\'' +
                ", totalAmount=" + totalAmount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
