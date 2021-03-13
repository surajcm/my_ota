package com.online.travel.air.mapper.shop;

import com.online.travel.model.response.MyAirShoppingResponse;
import com.online.travel.model.response.Offers;
import com.online.travel.schema.response.shop.CarrierOffersType;
import com.online.travel.schema.response.shop.IATAAirShoppingRS;
import com.online.travel.schema.response.shop.OfferType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AirShopResponseMapper {

    public MyAirShoppingResponse mapShopResponse(final IATAAirShoppingRS response) {
        MyAirShoppingResponse airShoppingResponse = new MyAirShoppingResponse();
        airShoppingResponse.setTransactionId(response.getPayloadAttributes().getTrxID());
        if (! response.getResponse().getOffersGroup().getCarrierOffers().isEmpty()) {
            airShoppingResponse.setOffers(
                    populateOffers(response.getResponse().getOffersGroup().getCarrierOffers()));
        }
        return airShoppingResponse;
    }

    private List<Offers> populateOffers(List<CarrierOffersType> carrierOffers) {
        List<Offers> offers = new ArrayList<>();
        for (CarrierOffersType carrierOffersType :carrierOffers) {
            List<OfferType> offerTypes = carrierOffersType.getOffer();
            for (OfferType offerType: offerTypes) {
                Offers offer = new Offers();
                offer.setOfferID(offerType.getOfferID());
                offers.add(offer);
            }
        }
        return offers;
    }
}
