package com.online.travel.film.controller;

import com.online.travel.film.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilmController {

    @Autowired
    private FilmService filmService;

    /**
     * Request URL : http://localhost:8080/allfilms
     */
    @GetMapping("/allfilms")
    @ResponseBody
    public String getAllFilms() {
        return filmService.getAllFilms();
    }

    /**
     * Request URL : http://localhost:8080/film?id=1
     * id can take any value from 1 to 6
     */
    @GetMapping("/film")
    @ResponseBody
    public String getFilmById(@RequestParam String id) {
        return filmService.getFilmById(id);
    }
}
