package com.online.travel.dataaccess.serialization;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime unmarshal(final String dateVal) throws Exception {
        return ZonedDateTime.parse(dateVal);
    }

    @Override
    public String marshal(final ZonedDateTime dateVal) throws Exception {
        return Optional.ofNullable(dateVal).map(DateTimeFormatter.ISO_OFFSET_DATE_TIME::format).orElse(null);
    }
}