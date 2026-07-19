package com.online.travel.graphql;

import com.online.travel.air.service.AirOffersService;
import com.online.travel.air.service.AirShopService;
import com.online.travel.air.validator.AirShopValidator;
import com.online.travel.exception.MyOtaException;
import com.online.travel.graphql.input.AirShoppingRequestInput;
import com.online.travel.graphql.input.PassengersInput;
import com.online.travel.graphql.input.SliceInput;
import com.online.travel.model.referencedata.CabinTypeCode;
import com.online.travel.model.referencedata.PassengerType;
import com.online.travel.model.request.MyAirOffersRequest;
import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.model.request.Slice;
import com.online.travel.model.response.MyAirOffersResponse;
import com.online.travel.model.response.MyAirShoppingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MyOtaQueryResolver {

    private static final Map<String, CabinTypeCode> CABIN_CLASS_MAPPING = Map.of(
            "PREMIUM_FIRST", CabinTypeCode.P,
            "FIRST", CabinTypeCode.F,
            "PREMIUM_BUSINESS", CabinTypeCode.J,
            "BUSINESS", CabinTypeCode.C,
            "ECONOMY_STANDARD", CabinTypeCode.M,
            "PREMIUM_ECONOMY", CabinTypeCode.S,
            "ECONOMY", CabinTypeCode.Y);

    @Autowired
    private AirShopValidator validator;

    @Autowired
    private AirShopService airShopService;

    @Autowired
    private AirOffersService airOffersService;

    @QueryMapping
    public String hello(@Argument final String who) {
        return String.format("Hello, %s!", Optional.ofNullable(who).orElse("GraphQL"));
    }

    @QueryMapping
    public MyAirShoppingResponse flightSearch(@Argument final AirShoppingRequestInput request) {
        var shoppingRequest = toShoppingRequest(request);
        validator.validate(shoppingRequest);
        return airShopService.doAirShopping(shoppingRequest);
    }

    @QueryMapping
    public MyAirOffersResponse flightOffers() {
        return airOffersService.doAirOffers(new MyAirOffersRequest());
    }

    private MyAirShoppingRequest toShoppingRequest(final AirShoppingRequestInput request) {
        var shoppingRequest = new MyAirShoppingRequest();
        shoppingRequest.setSlices(toSlices(request.slices()));
        shoppingRequest.setPassenger(toPassengers(request.passengers()));
        shoppingRequest.setCabinTypeCode(toCabinTypeCode(request.cabinClass()));
        return shoppingRequest;
    }

    private List<Slice> toSlices(final List<SliceInput> sliceInputs) {
        List<Slice> slices = new ArrayList<>();
        if (sliceInputs == null) {
            return slices;
        }
        for (SliceInput sliceInput : sliceInputs) {
            var slice = new Slice();
            slice.setOrigin(sliceInput.origin());
            slice.setDestination(sliceInput.destination());
            slice.setDepartureDate(parseDate(sliceInput.departureDate()));
            slice.setCabinTypeCode(toCabinTypeCode(sliceInput.cabinClass()));
            slices.add(slice);
        }
        return slices;
    }

    private LocalDate parseDate(final String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException parseException) {
            throw new MyOtaException("Invalid date format for departureDate. Expected format: YYYY-MM-DD",
                    HttpStatus.BAD_REQUEST);
        }
    }

    private Map<Integer, PassengerType> toPassengers(final PassengersInput passengersInput) {
        Map<Integer, PassengerType> passengers = new HashMap<>();
        if (passengersInput == null) {
            return passengers;
        }
        int passengerId = 1;
        passengerId = addPassengers(passengers, passengerId, passengersInput.adults(), PassengerType.ADT);
        passengerId = addPassengers(passengers, passengerId, passengersInput.children(), PassengerType.CHD);
        addPassengers(passengers, passengerId, passengersInput.seniors(), PassengerType.SRC);
        return passengers;
    }

    private int addPassengers(final Map<Integer, PassengerType> passengers, final int startId,
                              final Integer count, final PassengerType passengerType) {
        int passengerId = startId;
        int remaining = Optional.ofNullable(count).orElse(0);
        while (remaining > 0) {
            passengers.put(passengerId, passengerType);
            passengerId++;
            remaining--;
        }
        return passengerId;
    }

    private CabinTypeCode toCabinTypeCode(final String cabinClass) {
        if (cabinClass == null) {
            return null;
        }
        return CABIN_CLASS_MAPPING.get(cabinClass);
    }
}
