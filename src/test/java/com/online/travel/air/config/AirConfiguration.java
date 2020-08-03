package com.online.travel.air.config;

import com.online.travel.air.connector.air.AirOffersConnector;
import com.online.travel.air.connector.air.AirShopConnector;
import com.online.travel.air.mapper.offers.AirOffersMapper;
import com.online.travel.air.mapper.shop.AirShopRequestMapper;
import com.online.travel.air.mapper.shop.AirShopResponseMapper;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

@ContextConfiguration
public class AirConfiguration {
    @Bean
    AirShopConnector airShopConnector() {
        return Mockito.mock(AirShopConnector.class);
    }

    @Bean
    RestTemplate restTemplate() {
        return Mockito.mock(RestTemplate.class);
    }

    @Bean
    AirShopResponseMapper airShopResponseMapper() {
        return Mockito.mock(AirShopResponseMapper.class);
    }

    @Bean
    AirOffersMapper airOffersMapper() {
        return Mockito.mock(AirOffersMapper.class);
    }

    @Bean
    AirOffersConnector airOffersConnector() {
        return Mockito.mock(AirOffersConnector.class);
    }

    @Bean
    AirShopRequestMapper airShopRequestMapper() {
        return Mockito.mock(AirShopRequestMapper.class);
    }
}
