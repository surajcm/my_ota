package com.online.travel.air.mapper.shop;

import com.online.travel.model.response.MyAirShoppingResponse;
import com.online.travel.schema.response.shop.IATAAirShoppingRS;
import org.springframework.stereotype.Component;

@Component
public class AirShopResponseMapper {

    public MyAirShoppingResponse mapShopResponse(final IATAAirShoppingRS response) {
        MyAirShoppingResponse airShoppingResponse = new MyAirShoppingResponse();
        //response.ge
        return airShoppingResponse;
    }
}
