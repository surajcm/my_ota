package com.online.travel.connector.air;

import com.online.travel.connector.RestConnector;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AirShopConnector extends RestConnector<String> {

    public String doShopping(String url) {
        ResponseEntity<String> responseEntity =  process(url,
                HttpMethod.POST,
                mockEntity(), String.class);
        return responseEntity.getBody();
    }

    private HttpEntity<String> mockEntity() {
        return new HttpEntity<>(getBody(), getHeaders());
    }

    private String getBody() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<IATA_AirShoppingRQ xmlns=\"http://www.iata.org/IATA/2015/00/2019.2/IATA_AirShoppingRQ\">\n    <MessageDoc>\n        <RefVersionNumber>1.0</RefVersionNumber>\n    </MessageDoc>\n    <Party>\n        <Participant>\n            <Aggregator>\n                <AggregatorID>88888888</AggregatorID>\n                <Name>JR TECHNOLOGIES</Name>\n            </Aggregator>\n        </Participant>\n        <Sender>\n            <TravelAgency>\n                <AgencyID>9A</AgencyID>\n                <IATA_Number>12312312</IATA_Number>\n                <Name>Gods Travel</Name>\n            </TravelAgency>\n        </Sender>\n    </Party>\n    <PayloadAttributes>\n        <EchoTokenText>6bca263e-d1e8-4e2d-a648-40a400003526</EchoTokenText>\n        <Timestamp>2001-12-17T09:30:47+05:00</Timestamp>\n        <TrxID>transaction497</TrxID>\n        <VersionNumber>2019.2</VersionNumber>\n    </PayloadAttributes>\n    <POS>\n        <City>\n            <IATA_LocationCode>ATH</IATA_LocationCode>\n        </City>\n        <Country>\n            <CountryCode>GR</CountryCode>\n        </Country>\n        <RequestTime>2020-10-12T07:38:00</RequestTime>\n    </POS>\n    <Request>\n        <FlightCriteria>\n            <OriginDestCriteria>\n                <DestArrivalCriteria>\n                    <IATA_LocationCode>LHR</IATA_LocationCode>\n                </DestArrivalCriteria>\n                <OriginDepCriteria>\n                    <Date>2020-09-20</Date>\n                    <IATA_LocationCode>BCN</IATA_LocationCode>\n                </OriginDepCriteria>\n            </OriginDestCriteria>\n            <OriginDestCriteria>\n                <DestArrivalCriteria>\n                    <IATA_LocationCode>BCN</IATA_LocationCode>\n                </DestArrivalCriteria>\n                <OriginDepCriteria>\n                    <Date>2020-09-27</Date>\n                    <IATA_LocationCode>LHR</IATA_LocationCode>\n                </OriginDepCriteria>\n                <PreferredCabinType>\n                    <CabinTypeCode>M</CabinTypeCode>\n                </PreferredCabinType>\n            </OriginDestCriteria>\n        </FlightCriteria>\n        <Paxs>\n            <Pax>\n                <PaxID>Pax1</PaxID>\n                <PTC>ADT</PTC>\n            </Pax>\n            <Pax>\n                <PaxID>Pax2</PaxID>\n                <PTC>CHD</PTC>\n            </Pax>\n        </Paxs>\n        <ShoppingCriteria>\n            <CabinTypeCriteria>\n                <CabinTypeCode>M</CabinTypeCode>\n            </CabinTypeCriteria>\n        </ShoppingCriteria>\n    </Request>\n</IATA_AirShoppingRQ>";
    }
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/xml");
        httpHeaders.add("Authorization-Key", "");
        return httpHeaders;
    }
}
