package com.online.travel.air.service;

import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.schema.IATAAirShoppingRS;

import java.util.Map;

public interface AirService {
    IATAAirShoppingRS doAirShopping(Map<String, String> params, MyAirShoppingRequest myAirShoppingRequest);
}
