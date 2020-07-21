package com.online.travel.air.service;

import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.schema.IATAAirShoppingRS;

public interface AirService {
    IATAAirShoppingRS doAirShopping(MyAirShoppingRequest myAirShoppingRequest);
}
