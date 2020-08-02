package com.online.travel.film.connector;

import com.online.travel.dataaccess.connector.RestConnector;
import com.online.travel.model.response.Film;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class FilmConnector extends RestConnector<Film> {

    final String uri = "https://swapi.dev/api/films/";

    public Film getFilmById(String id) {
        ResponseEntity<Film> responseEntity = process(uri+ id + "/",
                HttpMethod.GET,
                entity(), Film.class);
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
