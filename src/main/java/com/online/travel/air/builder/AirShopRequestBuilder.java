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

    private static final String CABIN_CLASS = "cabinClass";

    public MyAirShoppingRequest buildAirShoppingRequest(final Map<String, String> params) {
        var myAirShoppingRequest = new MyAirShoppingRequest();
        myAirShoppingRequest.setSlices(getSlices(params));
        myAirShoppingRequest.setPassenger(getPassengers(params));
        myAirShoppingRequest.setCabinTypeCode(getCabinType(params));
        return myAirShoppingRequest;
    }

    private CabinTypeCode getCabinType(final Map<String, String> params) {
        // &cabinClass=economy
        CabinTypeCode cabinTypeCode = null;
        if (params.containsKey(CABIN_CLASS)) {
            var val = params.get(CABIN_CLASS);
            cabinTypeCode = CabinTypeCode.fromDescription(val).orElse(null);
        }
        return cabinTypeCode;
    }

    private List<Slice> getSlices(final Map<String, String> params) {
        Map<String, Slice> slices = new HashMap<>();
        var sliceMap = getSliceOnlyMap(params);
        for (Map.Entry<String, String> entry : sliceMap.entrySet()) {
            String currentKey = entry.getKey().split("\\.")[0];
            Slice slice = getSlice(slices, currentKey);
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
            if (entry.getKey().contains(CABIN_CLASS)) {
                slice.setCabinTypeCode(CabinTypeCode.fromDescription(entry.getValue()).get());
            }
            slices.put(currentKey, slice);
        }
        return new ArrayList<>(slices.values());
    }

    private Slice getSlice(final Map<String, Slice> slices, final String currentKey) {
        Slice slice;
        if (slices.containsKey(currentKey)) {
            slice = slices.get(currentKey);
        } else {
            slice = new Slice();
        }
        return slice;
    }


    private Map<String, String> getSliceOnlyMap(final Map<String, String> params) {
        return params.entrySet().stream().filter(k -> k.getKey().contains("slice"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Integer, PassengerType> getPassengers(final Map<String, String> params) {
        //?adult=1&child=2
        Map<Integer, PassengerType> originalPassengers = new HashMap<>();
        originalPassengers.putAll(addPassenger(params, "adult", PassengerType.ADT));
        originalPassengers.putAll(addPassenger(params, "child", PassengerType.CHD));
        originalPassengers.putAll(addPassenger(params, "senior", PassengerType.SRC));
        return originalPassengers;
    }

    private Map<Integer, PassengerType> addPassenger(final Map<String, String> params,
                                                     final String type,
                                                     final PassengerType passengerType) {
        int passengerCount = params.size() + 1;
        Map<Integer, PassengerType> originalPassengers = new HashMap<>();
        if (params.containsKey(type)) {
            int count = Integer.parseInt(params.get(type));
            while (count > 0) {
                originalPassengers.put(passengerCount, passengerType);
                passengerCount++;
                count--;
            }
        }
        return originalPassengers;
    }
}
