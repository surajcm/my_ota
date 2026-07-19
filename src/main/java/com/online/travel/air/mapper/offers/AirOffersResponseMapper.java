package com.online.travel.air.mapper.offers;

import com.online.travel.model.response.MyAirOffersResponse;
import com.online.travel.model.response.PricedOffer;
import com.online.travel.model.response.PricedOfferItem;
import com.online.travel.schema.response.offer.AmountType;
import com.online.travel.schema.response.offer.IATAOfferPriceRS;
import com.online.travel.schema.response.offer.OfferItemType;
import com.online.travel.schema.response.offer.OfferType;
import com.online.travel.schema.response.offer.Price2Type;
import com.online.travel.schema.response.offer.PriceType;
import com.online.travel.schema.response.offer.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class AirOffersResponseMapper {
    private static final Logger logger = LoggerFactory.getLogger(AirOffersResponseMapper.class);

    public MyAirOffersResponse mapOffersResponse(final IATAOfferPriceRS response) {
        var offersResponse = new MyAirOffersResponse();
        if (response == null) {
            logger.warn("Received null offer price response");
            return offersResponse;
        }
        Optional.ofNullable(response.getPayloadAttributes())
                .ifPresent(attributes -> offersResponse.setTransactionId(attributes.getTrxID()));
        Optional.ofNullable(response.getResponse())
                .ifPresent(responseType -> populateResponse(offersResponse, responseType));
        return offersResponse;
    }

    private void populateResponse(final MyAirOffersResponse offersResponse, final ResponseType responseType) {
        Optional.ofNullable(responseType.getShoppingResponse())
                .ifPresent(shoppingResponse ->
                        offersResponse.setShoppingResponseId(shoppingResponse.getShoppingResponseRefID()));
        Optional.ofNullable(responseType.getPricedOffer())
                .ifPresent(pricedOffer -> offersResponse.setPricedOffer(mapPricedOffer(pricedOffer)));
    }

    private PricedOffer mapPricedOffer(final OfferType offerType) {
        var pricedOffer = new PricedOffer();
        pricedOffer.setOfferID(offerType.getOfferID());
        pricedOffer.setOwnerCode(offerType.getOwnerCode());
        pricedOffer.setValidatingCarrierCode(offerType.getValidatingCarrierCode());
        Optional.ofNullable(offerType.getTotalPrice())
                .map(PriceType::getTotalAmount)
                .ifPresent(amount -> applyAmount(pricedOffer::setTotalAmount, pricedOffer::setCurrency, amount));
        pricedOffer.setOfferItems(mapOfferItems(offerType.getOfferItem()));
        return pricedOffer;
    }

    private List<PricedOfferItem> mapOfferItems(final List<OfferItemType> offerItems) {
        if (offerItems == null) {
            return List.of();
        }
        return offerItems.stream().map(this::mapOfferItem).collect(Collectors.toList());
    }

    private PricedOfferItem mapOfferItem(final OfferItemType offerItemType) {
        var offerItem = new PricedOfferItem();
        offerItem.setOfferItemID(offerItemType.getOfferItemID());
        offerItem.setOfferItemTypeCode(offerItemType.getOfferItemTypeCode());
        Optional.ofNullable(offerItemType.getPrice())
                .map(Price2Type::getTotalAmount)
                .ifPresent(amount -> applyAmount(offerItem::setTotalAmount, offerItem::setCurrency, amount));
        return offerItem;
    }

    private void applyAmount(final Consumer<BigDecimal> amountSetter,
                             final Consumer<String> currencySetter,
                             final AmountType amount) {
        amountSetter.accept(amount.getValue());
        currencySetter.accept(amount.getCurCode());
    }
}
