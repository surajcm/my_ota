package com.online.travel.air.service;

import com.online.travel.model.request.MyAirOffersRequest;
import com.online.travel.model.response.MyAirOffersResponse;

public interface AirOffersService {
    MyAirOffersResponse doAirOffers(MyAirOffersRequest myAirOffersRequest);
}
