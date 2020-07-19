package com.online.travel.air.controller;

import com.online.travel.air.service.AirService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AirController {
    private static final Logger logger = LoggerFactory.getLogger(AirController.class);
    @Autowired
    private AirService airService;

    @GetMapping("/air/listings/")
    public ResponseEntity<Object> flightSearch(@RequestParam Map<String, String> params)
            throws Exception {
        logger.info("Inside the flight search");
        return new ResponseEntity<>(airService.doAirShopping(params), HttpStatus.OK);
    }
}
