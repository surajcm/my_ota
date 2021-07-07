### Air Listing request sample
#### Swagger UI
![screenshot](https://github.com/surajcm/my_ota/blob/master/dev/AirListingSwagger.png?raw=true)

#### Request
```json
{
  "slice1.origin":"LHR",
  "slice1.destination":"BCN",
  "slice1.departureDate":"2021-09-23",
  "slice2.origin":"BCN",
  "slice2.destination":"LHR",
  "slice2.departureDate":"2021-09-25",
  "slice2.cabinClass":"economy",
  "adult":"1",
  "child":"2"
}
```
#### IATA Response in json format

<details>
  <summary>
    Air Listings Response
  </summary>
    <a href="https://github.com/surajcm/my_ota/blob/main/dev/air_listing_response.json">air_listing_response.json</a>
</details>

#### IATA Response in XML format
<details>
  <summary>
    Air Listings Response
  </summary>
    <a href="https://github.com/surajcm/my_ota/blob/main/dev/iata_shop_response.xml">iata_shop_response.xml</a>
</details>