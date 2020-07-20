package com.online.travel.air.controller;

import com.online.travel.air.builder.AirShopRequestBuilder;
import com.online.travel.air.service.AirService;
import com.online.travel.air.validator.AirShopValidator;
import com.online.travel.model.request.MyAirShoppingRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "AIR APIs", description = "APIs for making AIR shop, reprice, reservation, itinerary view, cancellation")
public class AirController {
    private static final Logger logger = LoggerFactory.getLogger(AirController.class);

    @Autowired
    private AirShopRequestBuilder airShopRequestBuilder;

    @Autowired
    private AirShopValidator validator;

    @Autowired
    private AirService airService;

    @GetMapping(value = "/listings/", produces = "application/json")
    @ApiOperation(value = "Do an air shopping", response = ResponseEntity.class)
    @ApiModelProperty(value = "params", reference = "Map")
    public ResponseEntity<Object> flightSearch(@RequestParam Map<String, String> params)
            throws Exception {
        logger.info("Inside the flight search");
        MyAirShoppingRequest myAirShoppingRequest = airShopRequestBuilder.buildAirShoppingRequest(params);
        validator.validate(myAirShoppingRequest);

        return new ResponseEntity<>(airService.doAirShopping(params, myAirShoppingRequest), HttpStatus.OK);
    }
}
