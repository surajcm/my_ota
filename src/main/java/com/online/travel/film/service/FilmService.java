package com.online.travel.film.service;

import com.online.travel.model.response.Film;

public interface FilmService {
    String getAllFilms();

    Film getFilmById(String id);
}
