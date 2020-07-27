package com.online.travel.air.service;

import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.schema.response.shop.IATAAirShoppingRS;


public interface AirShopService {
    IATAAirShoppingRS doAirShopping(MyAirShoppingRequest myAirShoppingRequest);
}
