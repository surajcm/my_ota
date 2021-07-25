package com.online.travel.air.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.travel.air.connector.air.AirShopConnector;
import com.online.travel.air.mapper.shop.AirShopRequestMapper;
import com.online.travel.air.mapper.shop.AirShopResponseMapper;
import com.online.travel.air.service.AirShopService;
import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.model.response.MyAirShoppingResponse;
import com.online.travel.schema.response.shop.IATAAirShoppingRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirShopServiceImpl implements AirShopService {
    private static final Logger logger = LoggerFactory.getLogger(AirShopServiceImpl.class);

    @Autowired
    private AirShopRequestMapper airShopMapper;

    @Autowired
    private AirShopConnector airShopConnector;

    @Autowired
    private AirShopResponseMapper airShopResponseMapper;

    @Override
    public MyAirShoppingResponse doAirShopping(final MyAirShoppingRequest myAirShoppingRequest) {
        logger.info("Going to hit IATA endpoint");
        var iataAirShoppingRQ = airShopMapper.mapShopRequest(myAirShoppingRequest);
        var response = airShopConnector.doShopping(iataAirShoppingRQ);
        logger.debug(responseAsStringForLogging(response));
        var airShoppingResponse = airShopResponseMapper.mapShopResponse(response);
        logger.info(airShoppingResponse.toString());
        logger.info("Done... showing results");
        return airShoppingResponse;
    }

    private String responseAsStringForLogging(final IATAAirShoppingRS response) {
        var mapper = new ObjectMapper();
        String responseAsString;
        try {
            responseAsString = mapper.writeValueAsString(response);
        } catch (JsonProcessingException exception) {
            responseAsString = "parseError";
        }
        return responseAsString;
    }

}
