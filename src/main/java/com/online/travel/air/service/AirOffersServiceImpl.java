package com.online.travel.air.service;

import com.online.travel.air.builder.AirOffersRequestBuilder;
import com.online.travel.connector.air.AirOffersConnector;
import com.online.travel.schema.request.offer.IATAOfferPriceRQ;
import com.online.travel.schema.response.offer.IATAOfferPriceRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirOffersServiceImpl implements AirOffersService {
    private static final Logger logger = LoggerFactory.getLogger(AirOffersService.class);

    @Autowired
    private AirOffersRequestBuilder airOffersRequestBuilder;

    @Autowired
    private AirOffersConnector airOffersConnector;

    @Override
    public IATAOfferPriceRS doAirOffers(final Object obj) {
        logger.info("Going to hit iata");
        IATAOfferPriceRQ iataOfferPriceRQ = airOffersRequestBuilder.mockIATAOfferPriceRQ();
        IATAOfferPriceRS iataOfferPriceRS = airOffersConnector.doOffers(iataOfferPriceRQ);
        logger.info("Done... showing results");
        return iataOfferPriceRS;
    }
}
