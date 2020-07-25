package com.online.travel.air.exception;

import com.online.travel.exception.MyOtaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AirExceptionHandler {

    @ExceptionHandler({MyOtaException.class})
    public ResponseEntity<Object> handleAccessDeniedException(final MyOtaException ex, final WebRequest request) {
        //todo: define a format for error response
        return new ResponseEntity<>(ex.getErrorCode() + " : " + ex.getErrorMessage(),
                new HttpHeaders(),
                ex.getStatus());
    }
}
