package com.online.travel.air.mapper.offers;

import com.online.travel.model.request.MyAirOffersRequest;
import com.online.travel.schema.request.offer.AggregatorType;
import com.online.travel.schema.request.offer.CityType;
import com.online.travel.schema.request.offer.CountryType;
import com.online.travel.schema.request.offer.DataListsType;
import com.online.travel.schema.request.offer.DateTimeType;
import com.online.travel.schema.request.offer.IATAOfferPriceRQ;
import com.online.travel.schema.request.offer.IndividualType;
import com.online.travel.schema.request.offer.LoyaltyProgramAccountType;
import com.online.travel.schema.request.offer.MessageDocType;
import com.online.travel.schema.request.offer.POSType;
import com.online.travel.schema.request.offer.PartyType;
import com.online.travel.schema.request.offer.PaxListType;
import com.online.travel.schema.request.offer.PaxType;
import com.online.travel.schema.request.offer.PricedOfferType;
import com.online.travel.schema.request.offer.RecipientType;
import com.online.travel.schema.request.offer.RequestType;
import com.online.travel.schema.request.offer.SelectedOfferItemType;
import com.online.travel.schema.request.offer.SelectedOfferType;
import com.online.travel.schema.request.offer.SenderType;
import com.online.travel.schema.request.offer.TravelAgencyType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Component
public class AirOffersMapper {

    public IATAOfferPriceRQ buildIATAOfferPriceRQ(final MyAirOffersRequest myAirOffersRequest) {
        var iataOfferPriceRQ = new IATAOfferPriceRQ();
        iataOfferPriceRQ.setMessageDoc(messageDoc());
        iataOfferPriceRQ.setParty(party());
        iataOfferPriceRQ.setPOS(pos());
        iataOfferPriceRQ.setRequest(request(myAirOffersRequest));
        return iataOfferPriceRQ;
    }

    private RequestType request(final MyAirOffersRequest myAirOffersRequest) {
        var requestType = new RequestType();
        requestType.setDataLists(dataLists());
        requestType.setPricedOffer(pricedOffer());
        return requestType;
    }

    private PricedOfferType pricedOffer() {
        var pricedOfferType = new PricedOfferType();
        pricedOfferType.getSelectedOffer().add(selectedOffer());
        return pricedOfferType;
    }

    private SelectedOfferType selectedOffer() {
        var selectedOfferType = new SelectedOfferType();
        selectedOfferType.setOfferRefID("OFFER1");
        selectedOfferType.setOwnerCode("9A");
        selectedOfferType.getSelectedOfferItem().add(selectedOfferItem());
        selectedOfferType.setShoppingResponseRefID("201-2f6458138ec649349aeb09348ca3b547");
        return selectedOfferType;
    }

    private SelectedOfferItemType selectedOfferItem() {
        var selectedOfferItemType = new SelectedOfferItemType();
        selectedOfferItemType.setOfferItemRefID("OFFERITEM1_1");
        selectedOfferItemType.getPaxRefID().add("Pax1");
        return selectedOfferItemType;
    }

    private DataListsType dataLists() {
        var dataListsType = new DataListsType();
        dataListsType.setPaxList(paxList());
        return dataListsType;
    }

    private PaxListType paxList() {
        var paxListType = new PaxListType();
        paxListType.withPax(paxType());
        return paxListType;
    }

    private PaxType paxType() {
        var paxType = new PaxType();
        paxType.setIndividual(individual());
        paxType.withLoyaltyProgramAccount(loyaltyProgramAccount());
        paxType.setPaxID("Pax1");
        paxType.setPTC("ADT");
        return paxType;
    }

    private LoyaltyProgramAccountType loyaltyProgramAccount() {
        var loyaltyProgramAccountType = new LoyaltyProgramAccountType();
        loyaltyProgramAccountType.setAccountNumber("1234525525");
        return loyaltyProgramAccountType;
    }

    private IndividualType individual() {
        var individualType = new IndividualType();
        individualType.setSuffixName("Johan");
        individualType.setSurname("Smithas");
        individualType.setTitleName("Mr");
        return individualType;
    }

    private MessageDocType messageDoc() {
        var messageDocType = new MessageDocType();
        messageDocType.setRefVersionNumber(BigDecimal.valueOf(1.0));
        return messageDocType;
    }

    private PartyType party() {
        var party = new PartyType();
        var recipient = new RecipientType();
        recipient.setAggregator(aggregator());
        party.setRecipient(recipient);
        var sender = new SenderType();
        sender.setTravelAgency(travelAgency());
        party.setSender(sender);
        return party;
    }

    private TravelAgencyType travelAgency() {
        var travelAgencyType = new TravelAgencyType();
        travelAgencyType.setAgencyID("9A");
        travelAgencyType.setIATANumber(BigDecimal.valueOf(12312312));
        travelAgencyType.setName("Gods Travel");
        return travelAgencyType;
    }

    private AggregatorType aggregator() {
        var aggregatorType = new AggregatorType();
        aggregatorType.setAggregatorID("88888888");
        aggregatorType.setName("JR TECHNOLOGIES");
        return aggregatorType;
    }

    private POSType pos() {
        var pos = new POSType();
        var cityType = new CityType();
        cityType.setIATALocationCode("ATH");
        pos.setCity(cityType);
        var country = new CountryType();
        country.setCountryCode("GR");
        pos.setCountry(country);
        pos.setRequestTime(currentTimeStamp());
        return pos;
    }

    private DateTimeType currentTimeStamp() {
        var dateTimeType = new DateTimeType();
        dateTimeType.setValue(ZonedDateTime.now());
        return dateTimeType;
    }
}
