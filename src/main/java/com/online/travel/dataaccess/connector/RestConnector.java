package com.online.travel.dataaccess.connector;

import com.online.travel.exception.MyOtaException;
import com.online.travel.schema.error.ErrorsType;
import jakarta.xml.bind.JAXBElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class RestConnector<T> {
    private static final Logger logger = LoggerFactory.getLogger(RestConnector.class);
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<T> process(final String url,
                                     final HttpMethod method,
                                     final HttpEntity<?> requestEntity,
                                     final Class<T> responseType) throws MyOtaException {
        ResponseEntity<T> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, method, requestEntity, responseType);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            handleHttpException(ex, url);
            throw new MyOtaException("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR); // Never reached
        }
        //assuming all error responses are of same type
        if (responseEntity.getBody() instanceof JAXBElement) {
            return generateExceptionFromErrorMessage(responseEntity);
        }
        return responseEntity;
    }

    private void handleHttpException(final RuntimeException ex, final String url) {
        int statusCode = getStatusCode(ex);
        logger.error("External API error - Status: {}, URL: {}", statusCode, url);
        if (logger.isDebugEnabled()) {
            logger.debug("External API error details: {}", getResponseBody(ex));
        }
        throw new MyOtaException("Unable to process flight request. Please check your input and try again.",
                HttpStatus.valueOf(statusCode));
    }

    private int getStatusCode(final RuntimeException ex) {
        return ex instanceof HttpClientErrorException
                ? ((HttpClientErrorException) ex).getStatusCode().value()
                : ((HttpServerErrorException) ex).getStatusCode().value();
    }

    private String getResponseBody(final RuntimeException ex) {
        return ex instanceof HttpClientErrorException
                ? ((HttpClientErrorException) ex).getResponseBodyAsString()
                : ((HttpServerErrorException) ex).getResponseBodyAsString();
    }

    private ResponseEntity<T> generateExceptionFromErrorMessage(final ResponseEntity<T> responseEntity) {
        var errorResponse = (JAXBElement<ErrorsType>) responseEntity.getBody();
        String errorCode = errorResponse.getValue().getError().getCode();
        String errorValue = errorResponse.getValue().getError().getValue();

        logger.error("External API returned error - Code: {}", errorCode);
        // Log the full error in debug mode only
        if (logger.isDebugEnabled()) {
            logger.debug("External API error details: {}", errorValue);
        }

        // Don't expose external API error details to users
        throw new MyOtaException(errorCode,
                "Unable to process flight request. Please check your input and try again.",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}