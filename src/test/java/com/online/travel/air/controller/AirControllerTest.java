package com.online.travel.air.controller;

import com.online.travel.air.builder.AirOffersRequestBuilder;
import com.online.travel.air.builder.AirShopRequestBuilder;
import com.online.travel.air.config.AirConfiguration;
import com.online.travel.air.connector.air.AirShopConnector;
import com.online.travel.air.mapper.shop.AirShopRequestMapper;
import com.online.travel.air.mapper.shop.AirShopResponseMapper;
import com.online.travel.air.service.AirOffersService;
import com.online.travel.air.service.impl.AirOffersServiceImpl;
import com.online.travel.air.service.impl.AirShopServiceImpl;
import com.online.travel.air.validator.AirShopValidator;
import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.model.response.MyAirShoppingResponse;
import com.online.travel.schema.request.shop.IATAAirShoppingRQ;
import com.online.travel.schema.response.shop.IATAAirShoppingRS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {AirController.class,
        AirShopRequestBuilder.class,
        AirOffersRequestBuilder.class,
        AirShopValidator.class,
        AirShopServiceImpl.class,
        AirOffersService.class,
        AirOffersServiceImpl.class,
        AirConfiguration.class})
@WebMvcTest
class AirControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AirShopResponseMapper airShopResponseMapper;

    @Autowired
    private AirShopConnector airShopConnector;

    @Autowired
    private AirShopRequestMapper airShopRequestMapper;

    @Test
    public void verifyListings() throws Exception {
        when(airShopRequestMapper.mapShopRequest(any(MyAirShoppingRequest.class)))
                .thenReturn(new IATAAirShoppingRQ());
        when(airShopConnector.doShopping(any(IATAAirShoppingRQ.class)))
                .thenReturn(new IATAAirShoppingRS());
        when(airShopResponseMapper.mapShopResponse(any(IATAAirShoppingRS.class)))
                .thenReturn(new MyAirShoppingResponse());
        String uri = "/air/listings/";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .params(mockParams())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String resultCZ = mvcResult.getResponse().getContentAsString();
        Assertions.assertNotNull(resultCZ);
    }

    private MultiValueMap<String, String> mockParams() {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.put("slice1.origin", Collections.singletonList("LHR"));
        parameters.put("slice1.destination", Collections.singletonList("BCN"));
        parameters.put("slice1.departureDate", Collections.singletonList("2020-08-23"));
        parameters.put("slice2.origin", Collections.singletonList("BCN"));
        parameters.put("slice2.destination", Collections.singletonList("LHR"));
        parameters.put("slice2.departureDate", Collections.singletonList("2020-08-25"));
        parameters.put("slice2.cabinClass", Collections.singletonList("economy"));
        parameters.put("adult", Collections.singletonList("1"));
        parameters.put("child", Collections.singletonList("2"));
        parameters.put("cabinClass", Collections.singletonList("economy"));
        return parameters;
    }
}