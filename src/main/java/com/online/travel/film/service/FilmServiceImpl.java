package com.online.travel.film.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FilmServiceImpl implements FilmService {

    @Override
    public String getAllFilms() {
        final String uri = "https://swapi.dev/api/films/";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }

    @Override
    public String getFilmById(String id) {
        String uri = "https://swapi.dev/api/films/"+id+"/";
        RestTemplate restTemplate = new RestTemplate();
        String result = "";
        try {
            result = restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getLocalizedMessage();
        }
        return result;
    }
}
