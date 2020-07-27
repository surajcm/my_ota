package com.online.travel.dataaccess.serialization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class LocalDateAdapterTest {
    private static final LocalDateAdapter LOCAL_DATE_ADAPTER = new LocalDateAdapter();

    @Test
    public void successfulUnmarshal() throws Exception {
        String dateVal = "2012-08-19";
        LocalDate convertedDate = LOCAL_DATE_ADAPTER.unmarshal(dateVal);
        Assertions.assertEquals(2012, convertedDate.getYear());
        Assertions.assertEquals(8, convertedDate.getMonthValue());
        Assertions.assertEquals(19, convertedDate.getDayOfMonth());
    }

    @Test
    public void failedUnmarshal() {
        String dateVal = "2012-18-19";
        Assertions.assertThrows(DateTimeParseException.class, () -> LOCAL_DATE_ADAPTER.unmarshal(dateVal));
    }

    @Test
    public void successfulMarshal() throws Exception {
        LocalDate date = LocalDate.of(2012,8,22);
        String convertedDate = LOCAL_DATE_ADAPTER.marshal(date);
        Assertions.assertEquals(convertedDate, date.toString());
    }

}