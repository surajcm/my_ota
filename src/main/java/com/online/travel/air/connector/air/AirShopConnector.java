package com.online.travel.air.connector.air;


import com.online.travel.dataaccess.connector.RestConnector;
import com.online.travel.schema.request.shop.IATAAirShoppingRQ;
import com.online.travel.schema.response.shop.IATAAirShoppingRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
public class AirShopConnector extends RestConnector<IATAAirShoppingRS> {
    private static final Logger logger = LoggerFactory.getLogger(AirShopConnector.class);

    @Value("${air.shop.url}")
    private String shopUrl;

    @Value("${AUTH_KEY}")
    private String authKey;

    public IATAAirShoppingRS doShopping(final IATAAirShoppingRQ iataAirShoppingRQ) {
        logRequest(iataAirShoppingRQ);
        var responseEntity = process(shopUrl,
                HttpMethod.POST,
                mockEntity(iataAirShoppingRQ), IATAAirShoppingRS.class);
        logResponse(responseEntity.getBody());
        return responseEntity.getBody();
    }

    private void logResponse(final IATAAirShoppingRS body) {
        logger.info("logging response ");
        try {
            var contextObj = JAXBContext.newInstance(IATAAirShoppingRS.class);
            var marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            var sw = new StringWriter();
            marshallerObj.marshal(body, sw);
            var xmlContent = sw.toString();
            logger.info(xmlContent);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    private void logRequest(final IATAAirShoppingRQ iataAirShoppingRQ) {
        logger.info("logging request");
        try {
            var contextObj = JAXBContext.newInstance(IATAAirShoppingRQ.class);
            var marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            var sw = new StringWriter();
            marshallerObj.marshal(iataAirShoppingRQ, sw);
            var xmlContent = sw.toString();
            logger.info(xmlContent);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    private HttpEntity<IATAAirShoppingRQ> mockEntity(final IATAAirShoppingRQ iataAirShoppingRQ) {
        return new HttpEntity<>(iataAirShoppingRQ, getHeaders());
    }

    private HttpHeaders getHeaders() {
        var httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/xml");
        httpHeaders.add("Authorization-Key", authKey);
        return httpHeaders;
    }
}
