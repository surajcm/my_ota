package com.online.travel.air.connector.air;


import com.online.travel.dataaccess.connector.RestConnector;
import com.online.travel.exception.MyOtaException;
import com.online.travel.schema.request.shop.IATAAirShoppingRQ;
import com.online.travel.schema.response.shop.IATAAirShoppingRS;
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
public class AirShopConnector extends RestConnector<IATAAirShoppingRS> {
    private static final Logger logger = LoggerFactory.getLogger(AirShopConnector.class);

    @Value("${air.shop.url}")
    private String shopUrl;

    @Value("${AUTH_KEY:}")
    private String authKey;

    public IATAAirShoppingRS doShopping(final IATAAirShoppingRQ iataAirShoppingRQ) {
        validateAuthKey();
        logRequest(iataAirShoppingRQ);
        var responseEntity = process(shopUrl,
                HttpMethod.POST,
                mockEntity(iataAirShoppingRQ), IATAAirShoppingRS.class);
        logResponse(responseEntity.getBody());
        return responseEntity.getBody();
    }

    private void validateAuthKey() {
        if (!StringUtils.hasText(authKey)) {
            throw new MyOtaException("AUTH_KEY is not configured. Please set the AUTH_KEY environment variable.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void logResponse(final IATAAirShoppingRS body) {
        // Only log in debug mode to avoid sensitive data exposure
        if (logger.isDebugEnabled()) {
            logger.debug("Received air shopping response");
            logXml(body, IATAAirShoppingRS.class, "Response XML: {}");
        } else {
            logger.info("Air shopping response received successfully");
        }
    }

    private void logRequest(final IATAAirShoppingRQ iataAirShoppingRQ) {
        // Only log in debug mode to avoid sensitive data exposure
        if (logger.isDebugEnabled()) {
            logger.debug("Sending air shopping request");
            logXml(iataAirShoppingRQ, IATAAirShoppingRQ.class, "Request XML: {}");
        } else {
            logger.info("Sending air shopping request");
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
