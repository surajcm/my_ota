package com.online.travel.exception;

import org.springframework.http.HttpStatus;

public class MyOtaException extends RuntimeException {
    private final String errorMessage;
    private final String errorCode;
    private final HttpStatus status;

    public MyOtaException(final String errorMessage, final HttpStatus status) {
        this.errorMessage = errorMessage;
        this.status = status;
        this.errorCode = null;
    }

    public MyOtaException(final String errorCode, final String errorMessage, final HttpStatus status) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
