package com.online.travel.air.mapper.shop;

import com.online.travel.model.response.MyAirShoppingResponse;
import com.online.travel.model.response.Offers;
import com.online.travel.model.response.Segments;
import com.online.travel.schema.response.shop.CarrierOffersType;
import com.online.travel.schema.response.shop.DataListsType;
import com.online.travel.schema.response.shop.IATAAirShoppingRS;
import com.online.travel.schema.response.shop.OfferType;
import com.online.travel.schema.response.shop.PaxJourneyType;
import com.online.travel.schema.response.shop.PaxSegmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AirShopResponseMapper {
    private static final Logger logger = LoggerFactory.getLogger(AirShopResponseMapper.class);

    @Value("${air.shop.maxOffers}")
    private int maxOffers;

    public MyAirShoppingResponse mapShopResponse(final IATAAirShoppingRS response) {
        MyAirShoppingResponse airShoppingResponse = new MyAirShoppingResponse();
        airShoppingResponse.setTransactionId(response.getPayloadAttributes().getTrxID());
        if (!response.getResponse().getOffersGroup().getCarrierOffers().isEmpty()) {
            airShoppingResponse.setOffers(
                    populateOffers(response.getResponse().getOffersGroup().getCarrierOffers()));
        }
        if (!response.getResponse().getDataLists().getPaxSegmentList().getPaxSegment().isEmpty()) {
            airShoppingResponse.setSegments(
                    populateSegments(response.getResponse().getDataLists()));
        }
        airShoppingResponse.setTotalResultsCount(airShoppingResponse.getOffers().size());
        return airShoppingResponse;
    }

    private List<Segments> populateSegments(final DataListsType dataListsType) {
        List<PaxSegmentType> paxSegment = dataListsType.getPaxSegmentList().getPaxSegment();
        List<Segments> segments = paxSegment.stream().map(this::getSegments).collect(Collectors.toList());
        List<PaxJourneyType> paxJourneyTypes = dataListsType.getPaxJourneyList().getPaxJourney();
        for (Segments segment: segments) {
            Optional<PaxJourneyType> journeyWithSameSegment = paxJourneyTypes.stream()
                    .filter(p -> p.getPaxSegmentRefID().contains(segment.getSegmentID()))
                    .findFirst();
            journeyWithSameSegment.ifPresent(
                    paxJourneyType -> segment.setDuration(paxJourneyType.getDuration().toString()));
        }
        return segments;
    }

    private Segments getSegments(final PaxSegmentType paxSegmentType) {
        Segments newSegment = new Segments();
        newSegment.setSegmentID(paxSegmentType.getPaxSegmentID());
        return newSegment;
    }

    private List<Offers> populateOffers(final List<CarrierOffersType> carrierOffers) {
        logger.info("max offer count is :" + maxOffers);
        List<Offers> offers = new ArrayList<>();
        int count = 0;
        for (CarrierOffersType carrierOffersType : carrierOffers) {
            List<OfferType> offerTypes = carrierOffersType.getOffer();
            for (OfferType offerType : offerTypes) {
                if (maxOffers > count) {
                    offers.add(getOffers(offerType));
                    count++;
                }
            }
        }
        return offers;
    }

    private Offers getOffers(final OfferType offerType) {
        Offers offer = new Offers();
        offer.setOfferID(offerType.getOfferID());
        return offer;
    }
}
