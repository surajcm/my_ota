package com.online.travel.dataaccess.serialization;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

class ZonedDateTimeAdapterTest {
    private static final ZonedDateTimeAdapter ZONED_DATE_TIME_ADAPTER = new ZonedDateTimeAdapter();

    @Test
    public void successfulUnmarshal() throws Exception {
        String dateVal = "2018-12-26T20:28:33.213+05:30";
        ZonedDateTime convertedDate = ZONED_DATE_TIME_ADAPTER.unmarshal(dateVal);
        Assertions.assertEquals(2018, convertedDate.getYear());
        Assertions.assertEquals(12, convertedDate.getMonthValue());
        Assertions.assertEquals(26, convertedDate.getDayOfMonth());
        Assertions.assertEquals(20, convertedDate.getHour());
        Assertions.assertEquals(28, convertedDate.getMinute());
        Assertions.assertEquals(33, convertedDate.getSecond());
    }

    @Test
    public void failedUnmarshal() {
        String dateVal = "2018-13-26T20:28:33.213+05:30";
        Assertions.assertThrows(DateTimeParseException.class, () -> ZONED_DATE_TIME_ADAPTER.unmarshal(dateVal));
    }

    @Test
    public void successfulMarshal() throws Exception {
        LocalDate date = LocalDate.of(2012,8,22);
        LocalTime time = LocalTime.of(10,20);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(date,time, ZoneId.systemDefault());
        String convertedDate = ZONED_DATE_TIME_ADAPTER.marshal(zonedDateTime);
        Assertions.assertEquals(convertedDate, zonedDateTime.format(ISO_OFFSET_DATE_TIME));
    }
}