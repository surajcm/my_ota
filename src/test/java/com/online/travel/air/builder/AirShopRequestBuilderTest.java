package com.online.travel.air.builder;

import com.online.travel.model.request.MyAirShoppingRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class AirShopRequestBuilderTest {
    private final AirShopRequestBuilder airShopRequestBuilder = new AirShopRequestBuilder();

    @Test
    public void verifyBuildAirShoppingRequest() {
        Map<String, String> params = mockParams();
        MyAirShoppingRequest myAirShoppingRequest = airShopRequestBuilder
                .buildAirShoppingRequest(params);
        Assertions.assertNotNull(myAirShoppingRequest);
    }

    private Map<String, String> mockParams() {
        Map<String, String> params = new HashMap<>();
        params.put("slice1.origin","LHR");
        params.put("slice1.destination","BCN");
        params.put("slice1.departureDate","2020-08-23");
        params.put("slice2.origin","BCN");
        params.put("slice2.destination","LHR");
        params.put("slice2.departureDate","2020-08-25");
        params.put("slice2.cabinClass","economy");
        params.put("adult","1");
        params.put("child","2");
        params.put("cabinClass","economy");
        return params;
    }

}