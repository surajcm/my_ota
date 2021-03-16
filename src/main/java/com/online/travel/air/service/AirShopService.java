package com.online.travel.air.service;

import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.model.response.MyAirShoppingResponse;


public interface AirShopService {
    MyAirShoppingResponse doAirShopping(MyAirShoppingRequest myAirShoppingRequest);
}
