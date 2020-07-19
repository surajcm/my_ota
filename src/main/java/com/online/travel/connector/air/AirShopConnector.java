package com.online.travel.connector.air;


import com.online.travel.connector.RestConnector;
import com.online.travel.exception.MyOtaException;
import com.online.travel.model.Errors;
import com.online.travel.schema.IATAAirShoppingRQ;
import com.online.travel.schema.IATAAirShoppingRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class AirShopConnector extends RestConnector<String> {
    private static final Logger logger = LoggerFactory.getLogger(AirShopConnector.class);
    //todo: read this from a yml
    private String url = "http://iata.api.mashery.com/athena/ndc192api";

    public IATAAirShoppingRS doShopping(IATAAirShoppingRQ iataAirShoppingRQ) {
        logRequest(iataAirShoppingRQ);
        ResponseEntity<String> responseEntity = process(url,
                HttpMethod.POST,
                mockEntity(iataAirShoppingRQ), String.class);
        logger.info("response is : \n" + responseEntity.getBody());
        //logResponse(responseEntity.getBody());
        if (responseEntity.getBody() == null || responseEntity.getBody().contains("Errors")) {
            buildAndThrowException(responseEntity.getBody());
        }
        // convert to IATAAirShoppingRS instance
        return convertToIATAResponse(responseEntity.getBody());
    }

    private IATAAirShoppingRS convertToIATAResponse(String body) {
        IATAAirShoppingRS response = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(IATAAirShoppingRS.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(body);
            response = (IATAAirShoppingRS) jaxbUnmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
            //throw exception
        }
        return response;
    }

    private void buildAndThrowException(String body) {
        //bind to error response
        Errors errors = null;
        /*XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        xmlMapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));*/
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Errors.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(body);
            errors = (Errors) jaxbUnmarshaller.unmarshal(reader);
            throw new MyOtaException(errors.getErrorObject().getCode(),
                    errors.getErrorObject().getText(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JAXBException e) {
            e.printStackTrace();
            //throw exception
        }
    }

    private void logResponse(IATAAirShoppingRS body) {
        logger.info("logging response ");
        try {
            JAXBContext contextObj = JAXBContext.newInstance(IATAAirShoppingRS.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshallerObj.marshal(body, sw);
            String xmlContent = sw.toString();
            logger.info(xmlContent);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private HttpEntity<IATAAirShoppingRQ> mockEntity(IATAAirShoppingRQ iataAirShoppingRQ) {
        return new HttpEntity<>(iataAirShoppingRQ, getHeaders());
    }

    private void logRequest(IATAAirShoppingRQ iataAirShoppingRQ) {
        logger.info("logging request");
        try {
            JAXBContext contextObj = JAXBContext.newInstance(IATAAirShoppingRS.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshallerObj.marshal(iataAirShoppingRQ, sw);
            String xmlContent = sw.toString();
            logger.info(xmlContent);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private String getBody() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<IATA_AirShoppingRQ xmlns=\"http://www.iata.org/IATA/2015/00/2019.2/IATA_AirShoppingRQ\">\n " +
                "   <MessageDoc>\n        <RefVersionNumber>1.0</RefVersionNumber>\n    </MessageDoc>\n    " +
                "<Party>\n        " +
                "<Participant>\n            " +
                "<Aggregator>\n                <AggregatorID>88888888</AggregatorID>\n                " +
                "<Name>JR TECHNOLOGIES</Name>\n            </Aggregator>\n        </Participant>\n        " +
                "<Sender>\n            <TravelAgency>\n                " +
                "<AgencyID>9A</AgencyID>\n                <IATA_Number>12312312</IATA_Number>\n                " +
                "<Name>Gods Travel</Name>\n            </TravelAgency>\n        </Sender>\n    " +
                "</Party>\n    " +
                "<PayloadAttributes>\n        " +
                "<EchoTokenText>6bca263e-d1e8-4e2d-a648-40a400003526</EchoTokenText>\n        " +
                "<Timestamp>2001-12-17T09:30:47+05:00</Timestamp>\n        " +
                "<TrxID>transaction497</TrxID>\n        <VersionNumber>2019.2</VersionNumber>\n    " +
                "</PayloadAttributes>\n    " +
                "<POS>\n        <City>\n            <IATA_LocationCode>ATH</IATA_LocationCode>\n        </City>\n        " +
                "<Country>\n            <CountryCode>GR</CountryCode>\n        </Country>\n        " +
                "<RequestTime>2020-10-12T07:38:00</RequestTime>\n    " +
                "</POS>\n    <Request>\n        <FlightCriteria>\n            <OriginDestCriteria>\n                " +
                "<DestArrivalCriteria>\n                    <IATA_LocationCode>LHR</IATA_LocationCode>\n                " +
                "</DestArrivalCriteria>\n                <OriginDepCriteria>\n                    <Date>2020-09-20</Date>\n                    " +
                "<IATA_LocationCode>BCN</IATA_LocationCode>\n                </OriginDepCriteria>\n            " +
                "</OriginDestCriteria>\n            <OriginDestCriteria>\n                " +
                "<DestArrivalCriteria>\n                    <IATA_LocationCode>BCN</IATA_LocationCode>\n                " +
                "</DestArrivalCriteria>\n                " +
                "<OriginDepCriteria>\n                    <Date>2020-09-27</Date>\n                    " +
                "<IATA_LocationCode>LHR</IATA_LocationCode>\n                " +
                "</OriginDepCriteria>\n                " +
                "<PreferredCabinType>\n                    <CabinTypeCode>M</CabinTypeCode>\n                " +
                "</PreferredCabinType>\n            " +
                "</OriginDestCriteria>\n        </FlightCriteria>\n        <Paxs>\n            <Pax>\n                <PaxID>Pax1</PaxID>\n                <PTC>ADT</PTC>\n            </Pax>\n            <Pax>\n                <PaxID>Pax2</PaxID>\n                <PTC>CHD</PTC>\n            </Pax>\n        </Paxs>\n        <ShoppingCriteria>\n            <CabinTypeCriteria>\n                <CabinTypeCode>M</CabinTypeCode>\n            </CabinTypeCriteria>\n        </ShoppingCriteria>\n    </Request>\n</IATA_AirShoppingRQ>";
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/xml");
        httpHeaders.add("Authorization-Key", "smmmyezehs7ve3xt2m83m9ke");
        return httpHeaders;
    }
}
