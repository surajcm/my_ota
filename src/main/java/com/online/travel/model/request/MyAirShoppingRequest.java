package com.online.travel.model.request;

import com.online.travel.model.referencedata.PassengerType;

import java.util.List;
import java.util.Map;

public class MyAirShoppingRequest {
    private List<Slice> slices;
    private Map<Integer, PassengerType> passenger;

    public List<Slice> getSlices() {
        return slices;
    }

    public void setSlices(List<Slice> slices) {
        this.slices = slices;
    }

    public Map<Integer, PassengerType> getPassenger() {
        return passenger;
    }

    public void setPassenger(Map<Integer, PassengerType> passenger) {
        this.passenger = passenger;
    }
}
