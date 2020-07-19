package com.online.travel.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
    private String code;
    private String text;

    @JsonProperty("Code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
