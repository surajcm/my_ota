package com.online.travel.air.mapper;

import com.online.travel.model.referencedata.CabinTypeCode;
import com.online.travel.model.referencedata.PassengerType;
import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.model.request.Slice;
import com.online.travel.schema.AggregatorType;
import com.online.travel.schema.CabinTypeType2;
import com.online.travel.schema.CityType2;
import com.online.travel.schema.CountryType2;
import com.online.travel.schema.DateTimeType2;
import com.online.travel.schema.DestArrivalCriteriaType;
import com.online.travel.schema.FlightRequestType;
import com.online.travel.schema.IATAAirShoppingRQ;
import com.online.travel.schema.IATAPayloadStandardAttributesType2;
import com.online.travel.schema.MessageDocType2;
import com.online.travel.schema.OriginDepCriteriaType;
import com.online.travel.schema.OriginDestCriteriaType;
import com.online.travel.schema.POSType;
import com.online.travel.schema.PartyType;
import com.online.travel.schema.PaxType2;
import com.online.travel.schema.PaxsType;
import com.online.travel.schema.RecipientType;
import com.online.travel.schema.RequestType;
import com.online.travel.schema.SenderType;
import com.online.travel.schema.ShoppingCriteriaType;
import com.online.travel.schema.TravelAgencyType2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AirShopMapper {
    public IATAAirShoppingRQ mapShopRequest(MyAirShoppingRequest myAirShoppingRequest) {
        IATAAirShoppingRQ iataAirShoppingRQ = new IATAAirShoppingRQ();
        iataAirShoppingRQ.setMessageDoc(messageDoc());
        iataAirShoppingRQ.setParty(party());
        iataAirShoppingRQ.setPayloadAttributes(payloadAttributes());
        iataAirShoppingRQ.setPOS(pos());
        iataAirShoppingRQ.setRequest(request(myAirShoppingRequest));
        return iataAirShoppingRQ;
    }

    private RequestType request(MyAirShoppingRequest myAirShoppingRequest) {
        //?adult=1
        // &cabinClass=economy
        // &links=AD,ABF,ASM
        // &locale=en_US
        RequestType request = new RequestType();
        request.setFlightCriteria(flightCriteria(myAirShoppingRequest.getSlices()));
        request.setPaxs(paxs(myAirShoppingRequest.getPassenger()));
        request.setShoppingCriteria(shoppingCriteria(myAirShoppingRequest.getCabinTypeCode()));
        return request;
    }

    private ShoppingCriteriaType shoppingCriteria(CabinTypeCode cabinTypeCode) {
        ShoppingCriteriaType shoppingCriteriaType = new ShoppingCriteriaType();
        if (cabinTypeCode != null) {
            CabinTypeType2 cabinTypeType = new CabinTypeType2();
            cabinTypeType.setCabinTypeCode(cabinTypeCode.name());
            shoppingCriteriaType.getCabinTypeCriteria().add(cabinTypeType);
        }
        return shoppingCriteriaType;
    }

    private PaxsType paxs(Map<Integer, PassengerType> passengers) {
        PaxsType paxs = new PaxsType();
        paxs.getPax().addAll(paxsList(passengers));
        return paxs;
    }

    private List<PaxType2> paxsList(Map<Integer, PassengerType> passengers) {
        return passengers.entrySet().stream().map(this::buildPassenger).collect(Collectors.toList());
    }

    private PaxType2 buildPassenger(Map.Entry<Integer, PassengerType> passenger) {
        PaxType2 pax = new PaxType2();
        pax.setPaxID("Pax" + passenger.getKey());
        pax.setPTC(passenger.getValue().name());
        return pax;
    }

    private FlightRequestType flightCriteria(List<Slice> slices) {
        FlightRequestType flightRequestType = new FlightRequestType();
        flightRequestType.getOriginDestCriteria().addAll(originDestCriteria(slices));
        return flightRequestType;
    }

    private List<OriginDestCriteriaType> originDestCriteria(List<Slice> slices) {
        return slices.stream().map(this::originDestCriteria).collect(Collectors.toList());
    }

    private OriginDestCriteriaType originDestCriteria(Slice slice) {
        OriginDestCriteriaType originDestCriteria = new OriginDestCriteriaType();
        DestArrivalCriteriaType destArrivalCriteriaType = new DestArrivalCriteriaType();
        destArrivalCriteriaType.setIATALocationCode(slice.getOrigin());
        originDestCriteria.setDestArrivalCriteria(destArrivalCriteriaType);

        OriginDepCriteriaType originDepCriteria = new OriginDepCriteriaType();
        originDepCriteria.setIATALocationCode(slice.getDestination());
        originDepCriteria.setDate(slice.getDepartureDate());
        originDestCriteria.setOriginDepCriteria(originDepCriteria);
        if (slice.getCabinTypeCode() != null) {
            CabinTypeType2 cabinTypeType = new CabinTypeType2();
            cabinTypeType.setCabinTypeCode(slice.getCabinTypeCode().name());
            originDestCriteria.getPreferredCabinType().add(cabinTypeType);
        }
        return originDestCriteria;
    }


    private POSType pos() {
        POSType pos = new POSType();
        CityType2 cityType = new CityType2();
        cityType.setIATALocationCode("ATH");
        pos.setCity(cityType);
        CountryType2 country = new CountryType2();
        country.setCountryCode("GR");
        pos.setCountry(country);
        pos.setRequestTime(currentTimeStamp());
        return pos;
    }

    private IATAPayloadStandardAttributesType2 payloadAttributes() {
        IATAPayloadStandardAttributesType2 payloadAttributes = new IATAPayloadStandardAttributesType2();
        payloadAttributes.setEchoTokenText("6bca263e-d1e8-4e2d-a648-40a400003526");
        payloadAttributes.setTimestamp(currentTimeStamp());
        payloadAttributes.setTrxID("transaction497");
        payloadAttributes.setVersionNumber(BigDecimal.valueOf(2019.2));
        return payloadAttributes;
    }

    private DateTimeType2 currentTimeStamp() {
        DateTimeType2 dateTimeType = new DateTimeType2();
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

    private TravelAgencyType2 travelAgency() {
        TravelAgencyType2 travelAgencyType = new TravelAgencyType2();
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

    private MessageDocType2 messageDoc() {
        MessageDocType2 messageDoc = new MessageDocType2();
        messageDoc.setRefVersionNumber(BigDecimal.valueOf(1.0));
        return messageDoc;
    }
}
