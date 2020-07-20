package com.online.travel.connector.air;


import com.online.travel.connector.RestConnector;
import com.online.travel.schema.IATAAirShoppingRQ;
import com.online.travel.schema.IATAAirShoppingRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
public class AirShopConnector extends RestConnector<IATAAirShoppingRS> {
    private static final Logger logger = LoggerFactory.getLogger(AirShopConnector.class);
    //todo: read this from a yml
    private String url = "http://iata.api.mashery.com/athena/ndc192api";

    public IATAAirShoppingRS doShopping(IATAAirShoppingRQ iataAirShoppingRQ) {
        logRequest(iataAirShoppingRQ);
        ResponseEntity<IATAAirShoppingRS> responseEntity = process(url,
                HttpMethod.POST,
                mockEntity(iataAirShoppingRQ), IATAAirShoppingRS.class);
        //logResponse(responseEntity.getBody());
        return responseEntity.getBody();
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

    private HttpEntity<IATAAirShoppingRQ> mockEntity(IATAAirShoppingRQ iataAirShoppingRQ) {
        return new HttpEntity<>(iataAirShoppingRQ, getHeaders());
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/xml");
        httpHeaders.add("Authorization-Key", "");
        return httpHeaders;
    }
}
