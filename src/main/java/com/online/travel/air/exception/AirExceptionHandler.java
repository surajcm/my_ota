package com.online.travel.air.exception;

import com.online.travel.exception.MyOtaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AirExceptionHandler {

    @ExceptionHandler({MyOtaException.class})
    public ResponseEntity<Object> handleMyOtaException(final MyOtaException ex, final WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", true);
        errorResponse.put("message", ex.getErrorMessage());
        if (ex.getErrorCode() != null) {
            errorResponse.put("code", ex.getErrorCode());
        }
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), ex.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGenericException(final Exception ex, final WebRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", true);
        errorResponse.put("message", "An unexpected error occurred. Please try again later.");
        // Don't expose stack traces or internal error details to users
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
