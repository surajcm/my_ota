package com.online.travel.model.request;

import com.online.travel.model.referencedata.CabinTypeCode;

import java.time.LocalDate;

public class Slice {
    // &slice1.origin=LHR
    // &slice1.destination=BCN
    // &slice1.departureDate=2019-06-30
    // &slice1.cabinClass=economy
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private CabinTypeCode cabinTypeCode;

    public Slice() {
        //empty
    }
    public Slice(String origin, String destination, LocalDate departureDate) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
    }

    public Slice(String origin, String destination, LocalDate departureDate, CabinTypeCode cabinTypeCode) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.cabinTypeCode = cabinTypeCode;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public CabinTypeCode getCabinTypeCode() {
        return cabinTypeCode;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public void setCabinTypeCode(CabinTypeCode cabinTypeCode) {
        this.cabinTypeCode = cabinTypeCode;
    }
}
