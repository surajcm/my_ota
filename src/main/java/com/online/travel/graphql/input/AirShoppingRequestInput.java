package com.online.travel.graphql.input;

import java.util.List;

public record AirShoppingRequestInput(List<SliceInput> slices, PassengersInput passengers, String cabinClass) {
}
