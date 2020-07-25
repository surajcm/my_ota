package com.online.travel.model.request;

import com.online.travel.model.referencedata.CabinTypeCode;
import com.online.travel.model.referencedata.PassengerType;

import java.util.List;
import java.util.Map;

public class MyAirShoppingRequest {
    private List<Slice> slices;
    private Map<Integer, PassengerType> passenger;
    private CabinTypeCode cabinTypeCode;

    public List<Slice> getSlices() {
        return slices;
    }

    public void setSlices(final List<Slice> slices) {
        this.slices = slices;
    }

    public Map<Integer, PassengerType> getPassenger() {
        return passenger;
    }

    public void setPassenger(final Map<Integer, PassengerType> passenger) {
        this.passenger = passenger;
    }

    public CabinTypeCode getCabinTypeCode() {
        return cabinTypeCode;
    }

    public void setCabinTypeCode(final CabinTypeCode cabinTypeCode) {
        this.cabinTypeCode = cabinTypeCode;
    }
}
