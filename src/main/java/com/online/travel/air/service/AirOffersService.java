package com.online.travel.air.service;

import com.online.travel.model.request.MyAirOffersRequest;
import com.online.travel.schema.response.offer.IATAOfferPriceRS;

public interface AirOffersService {
    IATAOfferPriceRS doAirOffers(MyAirOffersRequest myAirOffersRequest);
}
