package com.online.travel.air.validator;

import com.online.travel.exception.MyOtaException;
import com.online.travel.model.request.MyAirShoppingRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AirShopValidator {
    public void validate(MyAirShoppingRequest myAirShoppingRequest) throws MyOtaException {
        //lets do the validation
        if (myAirShoppingRequest.getSlices().size() == 0 ) {
            throw new MyOtaException("400", HttpStatus.BAD_REQUEST);
        }
        if (myAirShoppingRequest.getPassenger().size() == 0 ) {
            throw new MyOtaException("400", HttpStatus.BAD_REQUEST);
        }
    }
}
