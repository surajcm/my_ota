package com.online.travel.air.service;


import com.online.travel.air.mapper.AirShopMapper;
import com.online.travel.connector.air.AirShopConnector;
import com.online.travel.schema.IATAAirShoppingRQ;
import com.online.travel.schema.IATAAirShoppingRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AirServiceImpl implements AirService {
    private static final Logger logger = LoggerFactory.getLogger(AirServiceImpl.class);
    @Autowired
    private AirShopConnector airShopConnector;

    @Autowired
    private AirShopMapper airShopMapper;

    @Override
    public IATAAirShoppingRS doAirShopping(Map<String, String> params) {
        logger.info("Going to hit iata");
        IATAAirShoppingRQ iataAirShoppingRQ = airShopMapper.mapShopRequest(params);
        logger.info("iataAirShoppingRQ is : "+iataAirShoppingRQ);

        IATAAirShoppingRS response = airShopConnector.doShopping(iataAirShoppingRQ);
        logger.info("Done... showing results");
        return response;
    }
}
