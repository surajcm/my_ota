package com.online.travel.air.controller;

import com.online.travel.air.builder.AirOffersRequestBuilder;
import com.online.travel.air.builder.AirShopRequestBuilder;
import com.online.travel.air.service.AirOffersService;
import com.online.travel.air.service.AirShopService;
import com.online.travel.air.validator.AirShopValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/air")
public class AirController {
    private static final Logger logger = LoggerFactory.getLogger(AirController.class);

    @Autowired
    private AirShopRequestBuilder airShopRequestBuilder;

    @Autowired
    private AirOffersRequestBuilder airOffersRequestBuilder;

    @Autowired
    private AirShopValidator validator;

    @Autowired
    private AirShopService airShopService;

    @Autowired
    private AirOffersService airOffersService;

    @GetMapping(value = "/listings/", produces = "application/json")
    public ResponseEntity<Object> flightSearch(@RequestParam final Map<String, String> params)
            throws Exception {
        logger.info("Inside the flight search");
        var myAirShoppingRequest = airShopRequestBuilder.buildAirShoppingRequest(params);
        validator.validate(myAirShoppingRequest);
        var airShoppingResponse = airShopService.doAirShopping(myAirShoppingRequest);
        return new ResponseEntity<>(airShoppingResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/offers/", produces = "application/json")
    public ResponseEntity<Object> flightOffers(@RequestParam final Map<String, String> params)
            throws Exception {
        logger.info("Inside the flight re-price");
        var myAirOffersRequest = airOffersRequestBuilder.buildAirOffersRequest(params);
        //todo: Use MyAirOffersResponse at controller layer
        var iataOfferPriceRS = airOffersService.doAirOffers(myAirOffersRequest);
        return new ResponseEntity<>(iataOfferPriceRS, HttpStatus.OK);
    }
}
