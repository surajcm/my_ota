package com.online.travel.air.service.impl;

import com.online.travel.air.connector.air.AirOffersConnector;
import com.online.travel.air.mapper.offers.AirOffersMapper;
import com.online.travel.air.mapper.offers.AirOffersResponseMapper;
import com.online.travel.air.service.AirOffersService;
import com.online.travel.model.request.MyAirOffersRequest;
import com.online.travel.model.response.MyAirOffersResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirOffersServiceImpl implements AirOffersService {
    private static final Logger logger = LoggerFactory.getLogger(AirOffersService.class);

    @Autowired
    private AirOffersMapper airOffersMapper;

    @Autowired
    private AirOffersResponseMapper airOffersResponseMapper;

    @Autowired
    private AirOffersConnector airOffersConnector;

    @Override
    public MyAirOffersResponse doAirOffers(final MyAirOffersRequest myAirOffersRequest) {
        logger.info("Going to hit iata");
        var iataOfferPriceRQ = airOffersMapper.buildIATAOfferPriceRQ(myAirOffersRequest);
        var iataOfferPriceRS = airOffersConnector.doOffers(iataOfferPriceRQ);
        logger.info("Done... showing results");
        return airOffersResponseMapper.mapOffersResponse(iataOfferPriceRS);
    }
}
