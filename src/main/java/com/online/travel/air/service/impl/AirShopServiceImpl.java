package com.online.travel.air.service.impl;


import com.online.travel.air.mapper.shop.AirShopRequestMapper;
import com.online.travel.air.mapper.shop.AirShopResponseMapper;
import com.online.travel.air.service.AirShopService;
import com.online.travel.connector.air.AirShopConnector;
import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.model.response.MyAirShoppingResponse;
import com.online.travel.schema.request.shop.IATAAirShoppingRQ;
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
    public IATAAirShoppingRS doAirShopping(final MyAirShoppingRequest myAirShoppingRequest) {
        logger.info("Going to hit iata");
        IATAAirShoppingRQ iataAirShoppingRQ = airShopMapper.mapShopRequest(myAirShoppingRequest);
        IATAAirShoppingRS response = airShopConnector.doShopping(iataAirShoppingRQ);
        logger.info("Done... showing results");
        MyAirShoppingResponse airShoppingResponse = airShopResponseMapper.mapShopResponse(response);
        logger.info(airShoppingResponse.toString());
        //todo: Return MyAirShoppingResponse
        return response;
    }
}
