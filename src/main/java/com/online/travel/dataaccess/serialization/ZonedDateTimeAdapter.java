package com.online.travel.dataaccess.serialization;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {
    @Override
    public ZonedDateTime unmarshal(String v) throws Exception {
        return ZonedDateTime.parse(v);
    }

    @Override
    public String marshal(ZonedDateTime v) throws Exception {
        return Optional.ofNullable(v).map(DateTimeFormatter.ISO_OFFSET_DATE_TIME::format).orElse(null);
    }
}