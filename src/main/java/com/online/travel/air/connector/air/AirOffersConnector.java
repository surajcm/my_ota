package com.online.travel.air.connector.air;

import com.online.travel.dataaccess.connector.RestConnector;
import com.online.travel.schema.request.offer.IATAOfferPriceRQ;
import com.online.travel.schema.response.offer.IATAOfferPriceRS;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

@Component
public class AirOffersConnector extends RestConnector<IATAOfferPriceRS> {
    private static final Logger logger = LoggerFactory.getLogger(AirShopConnector.class);

    @Value("${air.offer.url}")
    private String offersUrl;

    @Value("${AUTH_KEY}")
    private String authKey;

    public IATAOfferPriceRS doOffers(final IATAOfferPriceRQ iataOfferPriceRQ) {
        logRequest(iataOfferPriceRQ);
        var responseEntity = process(offersUrl,
                HttpMethod.POST,
                mockEntity(iataOfferPriceRQ), IATAOfferPriceRS.class);
        logResponse(responseEntity.getBody());
        return responseEntity.getBody();
    }

    private void logResponse(final IATAOfferPriceRS body) {
        logger.info("logging response ");
        try {
            var contextObj = JAXBContext.newInstance(IATAOfferPriceRS.class);
            var marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            var sw = new StringWriter();
            marshallerObj.marshal(body, sw);
            var xmlContent = sw.toString();
            logger.info(xmlContent);
        } catch (JAXBException ex) {
            logger.info("Error occurred :" + ex.getMessage());
        }
    }

    private void logRequest(final IATAOfferPriceRQ iataOfferPriceRQ) {
        logger.info("logging request");
        try {
            var contextObj = JAXBContext.newInstance(IATAOfferPriceRQ.class);
            var marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            var sw = new StringWriter();
            marshallerObj.marshal(iataOfferPriceRQ, sw);
            var xmlContent = sw.toString();
            logger.info(xmlContent);
        } catch (JAXBException ex) {
            logger.info("Error occurred :" + ex.getMessage());
        }
    }

    private HttpEntity<IATAOfferPriceRQ> mockEntity(final IATAOfferPriceRQ iataOfferPriceRQ) {
        return new HttpEntity<>(iataOfferPriceRQ, getHeaders());
    }

    private HttpHeaders getHeaders() {
        var httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/xml");
        httpHeaders.add("Authorization-Key", authKey);
        return httpHeaders;
    }
}
