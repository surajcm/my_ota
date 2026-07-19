package com.online.travel.model.response;

import java.math.BigDecimal;
import java.util.List;

public class PricedOffer {
    private String offerID;
    private String ownerCode;
    private String validatingCarrierCode;
    private BigDecimal totalAmount;
    private String currency;
    private List<PricedOfferItem> offerItems;

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(final String offerID) {
        this.offerID = offerID;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(final String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getValidatingCarrierCode() {
        return validatingCarrierCode;
    }

    public void setValidatingCarrierCode(final String validatingCarrierCode) {
        this.validatingCarrierCode = validatingCarrierCode;
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

    public List<PricedOfferItem> getOfferItems() {
        return offerItems;
    }

    public void setOfferItems(final List<PricedOfferItem> offerItems) {
        this.offerItems = offerItems;
    }

    @Override
    public String toString() {
        return "PricedOffer{" +
                "offerID='" + offerID + '\'' +
                ", ownerCode='" + ownerCode + '\'' +
                ", validatingCarrierCode='" + validatingCarrierCode + '\'' +
                ", totalAmount=" + totalAmount +
                ", currency='" + currency + '\'' +
                ", offerItems=" + offerItems +
                '}';
    }
}
