package com.online.travel.air.mapper;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AirShopMapper {
    public IATAAirShoppingRQ mapShopRequest(Map<String, String> params) {
        IATAAirShoppingRQ iataAirShoppingRQ = mockIATAShopReq();
        return iataAirShoppingRQ;
    }

    private IATAAirShoppingRQ mockIATAShopReq() {
        IATAAirShoppingRQ iataAirShoppingRQ = new IATAAirShoppingRQ();
        iataAirShoppingRQ.setMessageDoc(mockMessageDcc());
        iataAirShoppingRQ.setParty(mockParty());
        iataAirShoppingRQ.setPayloadAttributes(mockPayloadAttributes());
        iataAirShoppingRQ.setPOS(mockPOS());
        iataAirShoppingRQ.setRequest(mockRequest());
        return iataAirShoppingRQ;
    }

    private RequestType mockRequest() {
        RequestType request = new RequestType();
        request.setFlightCriteria(mockFlightCriteria());
        request.setPaxs(mockPaxs());
        request.setShoppingCriteria(mockShoppingCriteria());
        return request;
    }

    private ShoppingCriteriaType mockShoppingCriteria() {
        ShoppingCriteriaType shoppingCriteriaType = new ShoppingCriteriaType();
        CabinTypeType2 cabinTypeType = new CabinTypeType2();
        cabinTypeType.setCabinTypeCode("M");
        shoppingCriteriaType.getCabinTypeCriteria().add(cabinTypeType);
        return shoppingCriteriaType;
    }

    private PaxsType mockPaxs() {
        PaxsType paxs = new PaxsType();
        paxs.getPax().addAll(mockPaxsList());
        return paxs;
    }

    private List<PaxType2> mockPaxsList() {
        List<PaxType2> paxes = new ArrayList<>();
        PaxType2 pax1 = new PaxType2();
        pax1.setPaxID("Pax1");
        pax1.setPTC("ADT");
        PaxType2 pax2 = new PaxType2();
        pax2.setPaxID("Pax2");
        pax2.setPTC("CHD");
        paxes.add(pax1);
        paxes.add(pax2);
        return paxes;
    }

    private FlightRequestType mockFlightCriteria() {
        FlightRequestType flightRequestType = new FlightRequestType();
        flightRequestType.getOriginDestCriteria().addAll(mockOriginDestCriteria());

        return flightRequestType;
    }

    private List<OriginDestCriteriaType> mockOriginDestCriteria() {
        List<OriginDestCriteriaType> criteriaList = new ArrayList<>();
        criteriaList.add(firstOriginDestCriteria());
        criteriaList.add(secondOriginDestCriteria());
        return criteriaList;
    }

    private OriginDestCriteriaType firstOriginDestCriteria() {
        OriginDestCriteriaType originDestCriteria = new OriginDestCriteriaType();
        DestArrivalCriteriaType destArrivalCriteriaType = new DestArrivalCriteriaType();
        destArrivalCriteriaType.setIATALocationCode("LHR");
        originDestCriteria.setDestArrivalCriteria(destArrivalCriteriaType);

        OriginDepCriteriaType originDepCriteria = new OriginDepCriteriaType();
        originDepCriteria.setIATALocationCode("BCN");
        originDepCriteria.setDate(mockDate("2020-02-08"));
        originDestCriteria.setOriginDepCriteria(originDepCriteria);
        return originDestCriteria;
    }

    private OriginDestCriteriaType secondOriginDestCriteria() {
        OriginDestCriteriaType originDestCriteria = new OriginDestCriteriaType();
        DestArrivalCriteriaType destArrivalCriteria = new DestArrivalCriteriaType();
        destArrivalCriteria.setIATALocationCode("BCN");
        originDestCriteria.setDestArrivalCriteria(destArrivalCriteria);

        OriginDepCriteriaType originDepCriteria = new OriginDepCriteriaType();
        originDepCriteria.setIATALocationCode("LHR");
        originDepCriteria.setDate(mockDate("2020-07-09"));
        originDestCriteria.setOriginDepCriteria(originDepCriteria);
        CabinTypeType2 cabinTypeType = new CabinTypeType2();
        cabinTypeType.setCabinTypeCode("M");
        originDestCriteria.getPreferredCabinType().add(cabinTypeType);
        return originDestCriteria;
    }

    private LocalDate mockDate(String date) {
        return LocalDate.parse(date);
    }

    private POSType mockPOS() {
        POSType pos = new POSType();
        CityType2 cityType = new CityType2();
        cityType.setIATALocationCode("ATH");
        pos.setCity(cityType);
        CountryType2 country = new CountryType2();
        country.setCountryCode("GR");
        pos.setCountry(country);
        pos.setRequestTime(mockTimeStamp("2020-10-12"));
        return pos;
    }

    private IATAPayloadStandardAttributesType2 mockPayloadAttributes() {
        IATAPayloadStandardAttributesType2 payloadAttributes = new IATAPayloadStandardAttributesType2();
        payloadAttributes.setEchoTokenText("6bca263e-d1e8-4e2d-a648-40a400003526");
        payloadAttributes.setTimestamp(mockTimeStamp("2001-02-07"));
        payloadAttributes.setTrxID("transaction497");
        payloadAttributes.setVersionNumber(BigDecimal.valueOf(2019.2));
        return payloadAttributes;
    }

    private DateTimeType2 mockTimeStamp(String time) {
        DateTimeType2 dateTimeType = new DateTimeType2();
        dateTimeType.setValue(LocalDate.parse(time).atStartOfDay());
        return dateTimeType;
    }

    private PartyType mockParty() {
        PartyType party = new PartyType();
        RecipientType recipient = new RecipientType();
        recipient.setAggregator(mockAggregator());
        party.setRecipient(recipient);
        SenderType sender = new SenderType();
        sender.setTravelAgency(mockTravelAgency());
        party.setSender(sender);
        return party;
    }

    private TravelAgencyType2 mockTravelAgency() {
        TravelAgencyType2 travelAgencyType = new TravelAgencyType2();
        travelAgencyType.setAgencyID("9A");
        travelAgencyType.setIATANumber(BigDecimal.valueOf(12312312));
        travelAgencyType.setName("Gods Travel");
        return travelAgencyType;
    }

    private AggregatorType mockAggregator() {
        AggregatorType aggregatorType = new AggregatorType();
        aggregatorType.setAggregatorID("88888888");
        aggregatorType.setName("JR TECHNOLOGIES");
        return aggregatorType;
    }

    private MessageDocType2 mockMessageDcc() {
        MessageDocType2 messageDoc = new MessageDocType2();
        messageDoc.setRefVersionNumber(BigDecimal.valueOf(1.0));
        return messageDoc;
    }
}
