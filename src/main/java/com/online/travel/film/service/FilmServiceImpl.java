package com.online.travel.film.service;

import com.online.travel.film.connector.FilmConnector;
import com.online.travel.film.connector.FilmsConnector;
import com.online.travel.model.response.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmConnector filmConnector;

    @Autowired
    private FilmsConnector filmsConnector;

    @Override
    public String getAllFilms() {
        return filmsConnector.getAllFilmsData();
    }

    @Override
    public Film getFilmById(final String id) {
        return filmConnector.getFilmById(id);
    }
}
