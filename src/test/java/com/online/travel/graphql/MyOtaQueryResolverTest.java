package com.online.travel.graphql;

import com.online.travel.air.service.AirOffersService;
import com.online.travel.air.service.AirShopService;
import com.online.travel.air.validator.AirShopValidator;
import com.online.travel.model.request.MyAirShoppingRequest;
import com.online.travel.model.response.MyAirShoppingResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@GraphQlTest(MyOtaQueryResolver.class)
class MyOtaQueryResolverTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private AirShopValidator validator;

    @MockBean
    private AirShopService airShopService;

    @MockBean
    private AirOffersService airOffersService;

    @Test
    void helloReturnsGreeting() {
        graphQlTester.document("{ hello(who: \"NDC\") }")
                .execute()
                .path("hello")
                .entity(String.class)
                .isEqualTo("Hello, NDC!");
    }

    @Test
    void flightSearchReturnsMappedResponse() {
        var response = new MyAirShoppingResponse();
        response.setTransactionId("trx-123");
        response.setTotalResultsCount(2);
        when(airShopService.doAirShopping(any(MyAirShoppingRequest.class))).thenReturn(response);

        String document = """
                query {
                  flightSearch(request: {
                    slices: [{origin: "LHR", destination: "BCN", departureDate: "2099-06-30", cabinClass: ECONOMY}],
                    passengers: {adults: 1}
                  }) {
                    transactionId
                    totalResultsCount
                  }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("flightSearch.transactionId").entity(String.class).isEqualTo("trx-123")
                .path("flightSearch.totalResultsCount").entity(Integer.class).isEqualTo(2);
    }
}
