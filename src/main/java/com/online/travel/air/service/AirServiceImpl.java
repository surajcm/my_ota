package com.online.travel.air.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class AirServiceImpl implements AirService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String doAirShopping() {
        final String uri = "https://swapi.dev/api/films/";
        return restTemplate.getForObject(uri, String.class);
    }
}
