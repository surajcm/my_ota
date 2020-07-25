package com.online.travel.dataaccess.serialization;


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_DATE;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(final String date) throws Exception {
        return LocalDate.parse(date, ISO_DATE);
    }

    @Override
    public String marshal(final LocalDate date) throws Exception {
        return Optional.ofNullable(date).map(LocalDate::toString).orElse(null);
    }
}
