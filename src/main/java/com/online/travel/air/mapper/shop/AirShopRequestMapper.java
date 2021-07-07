package com.online.travel.air.mapper.shop;

import com.online.travel.model.referencedata.CabinTypeCode;
import com.online.travel.model.referencedata.PassengerType;
import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.model.request.Slice;
import com.online.travel.schema.request.shop.AggregatorType;
import com.online.travel.schema.request.shop.CabinTypeType;
import com.online.travel.schema.request.shop.CityType;
import com.online.travel.schema.request.shop.CountryType;
import com.online.travel.schema.request.shop.DateTimeType;
import com.online.travel.schema.request.shop.DestArrivalCriteriaType;
import com.online.travel.schema.request.shop.FlightRequestType;
import com.online.travel.schema.request.shop.IATAAirShoppingRQ;
import com.online.travel.schema.request.shop.IATAPayloadStandardAttributesType;
import com.online.travel.schema.request.shop.MessageDocType;
import com.online.travel.schema.request.shop.OriginDepCriteriaType;
import com.online.travel.schema.request.shop.OriginDestCriteriaType;
import com.online.travel.schema.request.shop.POSType;
import com.online.travel.schema.request.shop.PartyType;
import com.online.travel.schema.request.shop.PaxType;
import com.online.travel.schema.request.shop.PaxsType;
import com.online.travel.schema.request.shop.RecipientType;
import com.online.travel.schema.request.shop.RequestType;
import com.online.travel.schema.request.shop.SenderType;
import com.online.travel.schema.request.shop.ShoppingCriteriaType;
import com.online.travel.schema.request.shop.TravelAgencyType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AirShopRequestMapper {
    public IATAAirShoppingRQ mapShopRequest(final MyAirShoppingRequest myAirShoppingRequest) {
        var iataAirShoppingRQ = new IATAAirShoppingRQ();
        iataAirShoppingRQ.setMessageDoc(messageDoc());
        iataAirShoppingRQ.setParty(party());
        iataAirShoppingRQ.setPayloadAttributes(payloadAttributes());
        iataAirShoppingRQ.setPOS(pos());
        iataAirShoppingRQ.setRequest(request(myAirShoppingRequest));
        return iataAirShoppingRQ;
    }

    private RequestType request(final MyAirShoppingRequest myAirShoppingRequest) {
        //?adult=1
        // &cabinClass=economy
        // &links=AD,ABF,ASM
        // &locale=en_US
        var request = new RequestType();
        request.setFlightCriteria(flightCriteria(myAirShoppingRequest.getSlices()));
        request.setPaxs(paxs(myAirShoppingRequest.getPassenger()));
        request.setShoppingCriteria(shoppingCriteria(myAirShoppingRequest.getCabinTypeCode()));
        return request;
    }

    private ShoppingCriteriaType shoppingCriteria(final CabinTypeCode cabinTypeCode) {
        var shoppingCriteriaType = new ShoppingCriteriaType();
        if (cabinTypeCode != null) {
            var cabinTypeType = new CabinTypeType();
            cabinTypeType.setCabinTypeCode(cabinTypeCode.name());
            shoppingCriteriaType.getCabinTypeCriteria().add(cabinTypeType);
        }
        return shoppingCriteriaType;
    }

    private PaxsType paxs(final Map<Integer, PassengerType> passengers) {
        var paxs = new PaxsType();
        paxs.getPax().addAll(paxsList(passengers));
        return paxs;
    }

    private List<PaxType> paxsList(final Map<Integer, PassengerType> passengers) {
        return passengers.entrySet().stream().map(this::buildPassenger).collect(Collectors.toList());
    }

    private PaxType buildPassenger(final Map.Entry<Integer, PassengerType> passenger) {
        var pax = new PaxType();
        pax.setPaxID("Pax" + passenger.getKey());
        pax.setPTC(passenger.getValue().name());
        return pax;
    }

    private FlightRequestType flightCriteria(final List<Slice> slices) {
        var flightRequestType = new FlightRequestType();
        flightRequestType.getOriginDestCriteria().addAll(originDestCriteria(slices));
        return flightRequestType;
    }

    private List<OriginDestCriteriaType> originDestCriteria(final List<Slice> slices) {
        return slices.stream().map(this::originDestCriteria).collect(Collectors.toList());
    }

    private OriginDestCriteriaType originDestCriteria(final Slice slice) {
        var originDestCriteria = new OriginDestCriteriaType();
        originDestCriteria.setDestArrivalCriteria(getDestArrivalCriteriaType(slice));
        originDestCriteria.setOriginDepCriteria(getOriginDepCriteriaType(slice));
        if (slice.getCabinTypeCode() != null) {
            var cabinTypeType = new CabinTypeType();
            cabinTypeType.setCabinTypeCode(slice.getCabinTypeCode().name());
            originDestCriteria.getPreferredCabinType().add(cabinTypeType);
        }
        return originDestCriteria;
    }

    private OriginDepCriteriaType getOriginDepCriteriaType(final Slice slice) {
        var originDepCriteria = new OriginDepCriteriaType();
        originDepCriteria.setIATALocationCode(slice.getDestination());
        originDepCriteria.setDate(slice.getDepartureDate());
        return originDepCriteria;
    }

    private DestArrivalCriteriaType getDestArrivalCriteriaType(final Slice slice) {
        var destArrivalCriteriaType = new DestArrivalCriteriaType();
        destArrivalCriteriaType.setIATALocationCode(slice.getOrigin());
        return destArrivalCriteriaType;
    }


    private POSType pos() {
        POSType pos = new POSType();
        CityType cityType = new CityType();
        cityType.setIATALocationCode("ATH");
        pos.setCity(cityType);
        CountryType country = new CountryType();
        country.setCountryCode("GR");
        pos.setCountry(country);
        pos.setRequestTime(currentTimeStamp());
        return pos;
    }

    private IATAPayloadStandardAttributesType payloadAttributes() {
        IATAPayloadStandardAttributesType payloadAttributes = new IATAPayloadStandardAttributesType();
        payloadAttributes.setEchoTokenText("6bca263e-d1e8-4e2d-a648-40a400003526");
        payloadAttributes.setTimestamp(currentTimeStamp());
        payloadAttributes.setTrxID("transaction497");
        payloadAttributes.setVersionNumber(BigDecimal.valueOf(2019.2));
        return payloadAttributes;
    }

    private DateTimeType currentTimeStamp() {
        DateTimeType dateTimeType = new DateTimeType();
        dateTimeType.setValue(ZonedDateTime.now());
        return dateTimeType;
    }

    private PartyType party() {
        PartyType party = new PartyType();
        RecipientType recipient = new RecipientType();
        recipient.setAggregator(aggregator());
        party.setRecipient(recipient);
        SenderType sender = new SenderType();
        sender.setTravelAgency(travelAgency());
        party.setSender(sender);
        return party;
    }

    private TravelAgencyType travelAgency() {
        TravelAgencyType travelAgencyType = new TravelAgencyType();
        travelAgencyType.setAgencyID("9A");
        travelAgencyType.setIATANumber(BigDecimal.valueOf(12312312));
        travelAgencyType.setName("Gods Travel");
        return travelAgencyType;
    }

    private AggregatorType aggregator() {
        AggregatorType aggregatorType = new AggregatorType();
        aggregatorType.setAggregatorID("88888888");
        aggregatorType.setName("JR TECHNOLOGIES");
        return aggregatorType;
    }

    private MessageDocType messageDoc() {
        MessageDocType messageDoc = new MessageDocType();
        messageDoc.setRefVersionNumber(BigDecimal.valueOf(1.0));
        return messageDoc;
    }
}
