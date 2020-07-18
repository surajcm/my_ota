package com.online.travel.exception;

import org.springframework.http.HttpStatus;

public class MyOtaException extends RuntimeException {
    private final String errorMessage;
    private final HttpStatus errorCode;

    public MyOtaException(final String errorMessage, final HttpStatus errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
