package com.online.travel.connector.air;

import com.online.travel.connector.RestConnector;
import com.online.travel.schema.request.offer.IATAOfferPriceRQ;
import com.online.travel.schema.response.offer.IATAOfferPriceRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class AirOffersConnector extends RestConnector<IATAOfferPriceRS> {
    private static final Logger logger = LoggerFactory.getLogger(AirShopConnector.class);

    @Value("${air.offer.url}")
    private String offersUrl;

    @Value("${AUTH_KEY}")
    private String auth_key;

    public IATAOfferPriceRS doOffers(final IATAOfferPriceRQ iataOfferPriceRQ) {
        logRequest(iataOfferPriceRQ);
        ResponseEntity<IATAOfferPriceRS> responseEntity = process(offersUrl,
                HttpMethod.POST,
                mockEntity(iataOfferPriceRQ), IATAOfferPriceRS.class);
        logResponse(responseEntity.getBody());
        return responseEntity.getBody();
    }

    private void logResponse(final IATAOfferPriceRS body) {
        logger.info("logging response ");
        try {
            JAXBContext contextObj = JAXBContext.newInstance(IATAOfferPriceRS.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshallerObj.marshal(body, sw);
            String xmlContent = sw.toString();
            logger.info(xmlContent);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    private void logRequest(final IATAOfferPriceRQ iataOfferPriceRQ) {
        logger.info("logging request");
        try {
            JAXBContext contextObj = JAXBContext.newInstance(IATAOfferPriceRQ.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshallerObj.marshal(iataOfferPriceRQ, sw);
            String xmlContent = sw.toString();
            logger.info(xmlContent);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    private HttpEntity<IATAOfferPriceRQ> mockEntity(final IATAOfferPriceRQ iataOfferPriceRQ) {
        return new HttpEntity<>(iataOfferPriceRQ, getHeaders());
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/xml");
        httpHeaders.add("Authorization-Key", auth_key);
        return httpHeaders;
    }
}
