package com.online.travel.air.connector.air;

import com.online.travel.dataaccess.connector.RestConnector;
import com.online.travel.exception.MyOtaException;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.StringWriter;

@Component
public class AirOffersConnector extends RestConnector<IATAOfferPriceRS> {
    private static final Logger logger = LoggerFactory.getLogger(AirShopConnector.class);

    @Value("${air.offer.url}")
    private String offersUrl;

    @Value("${AUTH_KEY:}")
    private String authKey;

    public IATAOfferPriceRS doOffers(final IATAOfferPriceRQ iataOfferPriceRQ) {
        validateAuthKey();
        logRequest(iataOfferPriceRQ);
        var responseEntity = process(offersUrl,
                HttpMethod.POST,
                mockEntity(iataOfferPriceRQ), IATAOfferPriceRS.class);
        logResponse(responseEntity.getBody());
        return responseEntity.getBody();
    }

    private void validateAuthKey() {
        if (!StringUtils.hasText(authKey)) {
            throw new MyOtaException("AUTH_KEY is not configured. Please set the AUTH_KEY environment variable.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void logResponse(final IATAOfferPriceRS body) {
        // Only log in debug mode to avoid sensitive data exposure
        if (logger.isDebugEnabled()) {
            logger.debug("Received offer price response");
            logXml(body, IATAOfferPriceRS.class, "Response XML: {}");
        } else {
            logger.info("Offer price response received successfully");
        }
    }

    private void logRequest(final IATAOfferPriceRQ iataOfferPriceRQ) {
        // Only log in debug mode to avoid sensitive data exposure
        if (logger.isDebugEnabled()) {
            logger.debug("Sending offer price request");
            logXml(iataOfferPriceRQ, IATAOfferPriceRQ.class, "Request XML: {}");
        } else {
            logger.info("Sending offer price request");
        }
    }

    private <R> void logXml(final R object, final Class<R> clazz, final String message) {
        try {
            var contextObj = JAXBContext.newInstance(clazz);
            var marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            var sw = new StringWriter();
            marshallerObj.marshal(object, sw);
            var xmlContent = sw.toString();
            logger.debug(message, xmlContent);
        } catch (JAXBException ex) {
            logger.error("Failed to marshal object for logging", ex);
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
