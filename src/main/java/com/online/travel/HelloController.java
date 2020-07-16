package com.online.travel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    /*
     * Request URL : http://localhost:8080/
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    /*
     * Request URL : http://localhost:8080/allfilms
     */
    @GetMapping("/allfilms")
    @ResponseBody
    public String getAllFilms() {
        final String uri = "https://swapi.dev/api/films/";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }

    /*
     * Request URL : http://localhost:8080/film?id=1
     * id can take any value from 1 to 6
     */
    @GetMapping("/film")
    @ResponseBody
    public String getFilmById(@RequestParam String id) {
        String uri = "https://swapi.dev/api/films/"+id+"/";
        RestTemplate restTemplate = new RestTemplate();
        String result = "";
        try {
            result = restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getLocalizedMessage();
        } finally {
            return result;
        }
    }
}
