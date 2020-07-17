package com.online.travel.air.controller;

import com.online.travel.air.service.AirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirController {

    @Autowired
    private AirService airService;

    @GetMapping("/air/listings/")
    @ResponseBody
    public String airShop() {
        return airService.doAirShopping();
    }
}
