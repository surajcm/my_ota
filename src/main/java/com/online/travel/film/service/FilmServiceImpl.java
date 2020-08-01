package com.online.travel.film.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class FilmServiceImpl implements FilmService {

    final String uri = "https://swapi.dev/api/films/";

    @Override
    public String getAllFilms() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getFilmById(final String id) {
        String uriById = uri + id + "/";
        RestTemplate restTemplate = new RestTemplate();
        String result = "";
        try {
            result = restTemplate.getForObject(uriById, String.class);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            result = ex.getLocalizedMessage();
        }
        return result;
    }
}
