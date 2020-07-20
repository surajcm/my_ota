package com.online.travel.air.builder;

import com.online.travel.model.referencedata.CabinTypeCode;
import com.online.travel.model.referencedata.PassengerType;
import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.model.request.Slice;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AirShopRequestBuilder {

    public MyAirShoppingRequest buildAirShoppingRequest(Map<String, String> params) {
        MyAirShoppingRequest myAirShoppingRequest = new MyAirShoppingRequest();
        //Map<String, String> params2 = buildMockParams();
        myAirShoppingRequest.setSlices(getSlices(params));
        myAirShoppingRequest.setPassenger(getPassengers(params));
        return myAirShoppingRequest;
    }

    private Map<String, String> buildMockParams() {
        Map<String, String> mockParams = new HashMap<>();
        /*
        ?slice1.origin=LHR
        &slice1.destination=BCN
        &slice1.departureDate=2020-08-23
        &slice2.origin=BCN
        &slice2.destination=LHR
        &slice2.departureDate=2020-08-25
        &slice2.cabinClass=economy
        */

        mockParams.put("slice1.origin", "LHR");
        mockParams.put("slice1.destination", "BCN");
        mockParams.put("slice1.departureDate", "2020-08-23");
        mockParams.put("slice2.origin", "BCN");

        mockParams.put("slice2.destination", "LHR");
        mockParams.put("slice2.departureDate", "2020-08-25");
        mockParams.put("slice2.cabinClass", "economy");
        return mockParams;
    }

    private List<Slice> getSlices(Map<String, String> params) {
        Map<String, String> sliceMap = getSliceOnlyMap(params);
        return createListOfSliceMaps(sliceMap);
    }

    private List<Slice> createListOfSliceMaps(Map<String, String> sliceMap) {
        Map<String, Slice> slices = new HashMap<>();

        for (Map.Entry<String, String> entry : sliceMap.entrySet()) {
            String currentKey = entry.getKey().split("\\.")[0];
            Slice slice;
            if (slices.containsKey(currentKey)) {
                slice = slices.get(currentKey);
            } else {
                slice = new Slice();
            }
            if (entry.getKey().contains("origin")) {
                slice.setOrigin(entry.getValue());
            }
            if (entry.getKey().contains("destination")) {
                slice.setDestination(entry.getValue());
            }
            if (entry.getKey().contains("departureDate")) {
                //todo : handle date parse error
                slice.setDepartureDate(LocalDate.parse(entry.getValue()));
            }
            if (entry.getKey().contains("cabinClass")) {
                slice.setCabinTypeCode(CabinTypeCode.fromDescription(entry.getValue()).get());
            }
            slices.put(currentKey, slice);
        }
        return new ArrayList<>(slices.values());
    }


    private Map<String, String> getSliceOnlyMap(Map<String, String> params) {
        return params.entrySet().stream().filter(k -> k.getKey().contains("slice"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Integer, PassengerType> getPassengers(Map<String, String> params) {
        //?adult=1&child=2
        //todo: build with actual params
        Map<Integer, PassengerType> passengers = new HashMap<>();
        passengers.put(1, PassengerType.ADT);
        passengers.put(2, PassengerType.CHD);
        return passengers;
    }
}
