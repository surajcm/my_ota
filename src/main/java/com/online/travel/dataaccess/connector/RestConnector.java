package com.online.travel.dataaccess.connector;

import com.online.travel.exception.MyOtaException;
import com.online.travel.schema.error.ErrorsType;
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

import javax.xml.bind.JAXBElement;

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
            logger.error("Error occurred: {}", ex.getMessage());
            throw new MyOtaException(ex.getResponseBodyAsString(), ex.getStatusCode());
        }
        //assuming all error responses are of same type
        if (responseEntity.getBody() instanceof JAXBElement) {
            return generateExceptionFromErrorMessage(responseEntity);
        }
        return responseEntity;
    }

    private ResponseEntity<T> generateExceptionFromErrorMessage(final ResponseEntity<T> responseEntity) {
        var errorResponse = (JAXBElement<ErrorsType>) responseEntity.getBody();
        logger.info("An error occurred : {}" , errorResponse.getValue().getError().getValue());
        throw new MyOtaException(errorResponse.getValue().getError().getCode(),
                errorResponse.getValue().getError().getValue(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}