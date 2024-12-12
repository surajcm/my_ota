package com.online.travel.graphql;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyOtaQueryResolver {

    public String hello(final String who) {
        return String.format("Hello, %s!", Optional.ofNullable(who).orElse("GraphQL"));
    }
}
