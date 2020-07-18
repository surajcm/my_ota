package com.online.travel.air.service;


import com.online.travel.connector.air.AirShopConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirServiceImpl implements AirService {
    private static final Logger logger = LoggerFactory.getLogger(AirServiceImpl.class);
    @Autowired
    private AirShopConnector airShopConnector;

    @Override
    public String doAirShopping() {
        logger.info("Going to hit iata");
        String url = "http://iata.api.mashery.com/athena/ndc192api";
        String response = airShopConnector.doShopping(url);
        logger.info(response);
        return response;
    }
}
