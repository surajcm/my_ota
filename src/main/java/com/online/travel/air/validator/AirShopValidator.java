package com.online.travel.air.validator;

import com.online.travel.exception.MyOtaException;
import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.model.request.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Component
public class AirShopValidator {
    private static final Pattern IATA_CODE_PATTERN = Pattern.compile("^[A-Z]{3}$");
    private static final int MAX_PASSENGERS = 99;
    private static final int MAX_DAYS_IN_FUTURE = 365;

    public void validate(final MyAirShoppingRequest myAirShoppingRequest) throws MyOtaException {
        if (myAirShoppingRequest.getSlices() == null || myAirShoppingRequest.getSlices().isEmpty()) {
            throw new MyOtaException("At least one flight slice is required", HttpStatus.BAD_REQUEST);
        }

        if (myAirShoppingRequest.getPassenger() == null || myAirShoppingRequest.getPassenger().isEmpty()) {
            throw new MyOtaException("At least one passenger is required", HttpStatus.BAD_REQUEST);
        }

        if (myAirShoppingRequest.getPassenger().size() > MAX_PASSENGERS) {
            throw new MyOtaException("Maximum " + MAX_PASSENGERS + " passengers allowed", HttpStatus.BAD_REQUEST);
        }

        for (Slice slice : myAirShoppingRequest.getSlices()) {
            validateSlice(slice);
        }
    }

    private void validateSlice(final Slice slice) {
        validateAirportCodes(slice);
        validateDepartureDate(slice);
    }

    private void validateAirportCodes(final Slice slice) {
        if (slice.getOrigin() == null || !IATA_CODE_PATTERN.matcher(slice.getOrigin()).matches()) {
            throw new MyOtaException("Invalid origin airport code. Must be 3-letter IATA code",
                    HttpStatus.BAD_REQUEST);
        }

        if (slice.getDestination() == null || !IATA_CODE_PATTERN.matcher(slice.getDestination()).matches()) {
            throw new MyOtaException("Invalid destination airport code. Must be 3-letter IATA code",
                    HttpStatus.BAD_REQUEST);
        }

        if (slice.getOrigin().equals(slice.getDestination())) {
            throw new MyOtaException("Origin and destination cannot be the same", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateDepartureDate(final Slice slice) {
        if (slice.getDepartureDate() == null) {
            throw new MyOtaException("Departure date is required", HttpStatus.BAD_REQUEST);
        }

        LocalDate today = LocalDate.now();
        if (slice.getDepartureDate().isBefore(today)) {
            throw new MyOtaException("Departure date cannot be in the past", HttpStatus.BAD_REQUEST);
        }

        if (slice.getDepartureDate().isAfter(today.plusDays(MAX_DAYS_IN_FUTURE))) {
            throw new MyOtaException("Departure date cannot be more than " + MAX_DAYS_IN_FUTURE + " days in the future",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
