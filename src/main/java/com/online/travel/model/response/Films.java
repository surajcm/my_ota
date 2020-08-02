package com.online.travel.model.response;

import java.util.ArrayList;
import java.util.List;

public class Films {
    private int count;
    private Film next;
    private Film previous;
    private List<Film> results = new ArrayList<>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Film getNext() {
        return next;
    }

    public void setNext(Film next) {
        this.next = next;
    }

    public Film getPrevious() {
        return previous;
    }

    public void setPrevious(Film previous) {
        this.previous = previous;
    }

    public List<Film> getResults() {
        return results;
    }

    public void setResults(List<Film> results) {
        this.results = results;
    }
}
