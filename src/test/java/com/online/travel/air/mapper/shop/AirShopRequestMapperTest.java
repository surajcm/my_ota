package com.online.travel.air.mapper.shop;

import com.online.travel.model.referencedata.CabinTypeCode;
import com.online.travel.model.referencedata.PassengerType;
import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.model.request.Slice;
import com.online.travel.schema.request.shop.IATAAirShoppingRQ;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AirShopRequestMapperTest {
    private final AirShopRequestMapper airShopRequestMapper = new AirShopRequestMapper();

    @Test
    public void verifyMapShopRequest() {
        MyAirShoppingRequest myAirShoppingRequest = mockMyAirShoppingRequest();
        IATAAirShoppingRQ iataAirShoppingRQ = airShopRequestMapper
                .mapShopRequest(myAirShoppingRequest);
        Assertions.assertNotNull(iataAirShoppingRQ);
    }

    private MyAirShoppingRequest mockMyAirShoppingRequest() {
        MyAirShoppingRequest myAirShoppingRequest = new MyAirShoppingRequest();
        myAirShoppingRequest.setCabinTypeCode(CabinTypeCode.Y);
        myAirShoppingRequest.setSlices(mockSlices());
        myAirShoppingRequest.setPassenger(mockPassenger());
        return myAirShoppingRequest;
    }

    private Map<Integer, PassengerType> mockPassenger() {
        Map<Integer, PassengerType> passengers = new HashMap<>();
        passengers.put(2, PassengerType.ADT);
        passengers.put(1, PassengerType.CHD);
        return passengers;
    }

    private List<Slice> mockSlices() {
        List<Slice> slices = new ArrayList<>();
        slices.add(new Slice("ORD", "LAS", LocalDate.now().plusMonths(2)));
        slices.add(new Slice("LAS", "ORD",
                LocalDate.now().plusMonths(2).plusDays(2),
                CabinTypeCode.Y));
        return slices;
    }

}