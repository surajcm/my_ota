package com.online.travel.film.connector;

import com.online.travel.dataaccess.connector.RestConnector;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FilmsConnector extends RestConnector<String> {

    final String uri = "https://swapi.dev/api/films/";

    public String getAllFilmsData() {
        ResponseEntity<String> responseEntity = process(uri,
                HttpMethod.GET,
                entity(), String.class);
        return responseEntity.getBody();
    }

    private HttpEntity<String> entity() {
        return new HttpEntity<>(getHeaders());
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        return httpHeaders;
    }

}