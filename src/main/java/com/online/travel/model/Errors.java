package com.online.travel.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Errors {
    private Error ErrorObject;
    private String TimeStamp;
    private String TransactionIdentifier;

    @JsonProperty("Error")
    public Error getErrorObject() {
        return ErrorObject;
    }

    public void setErrorObject(Error errorObject) {
        ErrorObject = errorObject;
    }

    @JsonProperty("TimeStamp")
    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    @JsonProperty("TransactionIdentifier")
    public String getTransactionIdentifier() {
        return TransactionIdentifier;
    }

    public void setTransactionIdentifier(String transactionIdentifier) {
        TransactionIdentifier = transactionIdentifier;
    }
}
