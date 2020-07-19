package com.online.travel.connector;

import com.online.travel.exception.MyOtaException;
import com.online.travel.schema.ErrorsType;
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
import java.util.Map;
import java.util.stream.Collectors;

public class RestConnector<T> {
    private static final Logger logger = LoggerFactory.getLogger(RestConnector.class);
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<T> process(String url, HttpMethod method,
                                     HttpEntity<?> requestEntity,
                                     Class<T> responseType) throws MyOtaException {
        ResponseEntity<T> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, method, requestEntity, responseType);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new MyOtaException(e.getResponseBodyAsString(), e.getStatusCode());
        }

        //assuming all error responses are of same type
        if (responseEntity.getBody() instanceof JAXBElement) {
            JAXBElement<ErrorsType> errorResponse = (JAXBElement<ErrorsType>) responseEntity.getBody();
            logger.info("An error occurred :" + errorResponse.getValue().getError().getValue());
            throw new MyOtaException(errorResponse.getValue().getError().getCode(),
                    errorResponse.getValue().getError().getValue(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    public String buildRequestUrl(String url, Map<String, String> requestParams, String airOfferToken) {
        StringBuilder requestUri = new StringBuilder();
        if (airOfferToken != null) {
            requestUri.append(url).append("/").append(airOfferToken);
        } else {
            requestUri.append(url).append("?").append(buildRequestParameters(requestParams));
        }
        return requestUri.toString();
    }
    private String buildRequestParameters(Map<String, String> requestParams) {
        return requestParams.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }


}