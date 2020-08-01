package com.online.travel.air.builder;

import com.online.travel.model.request.MyAirOffersRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AirOffersRequestBuilder {

    public MyAirOffersRequest buildAirOffersRequest(final Map<String, String> params) {
        return new MyAirOffersRequest();
    }
}
