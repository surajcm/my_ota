package com.online.travel.film.service;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HelloQueryResolver implements GraphQLQueryResolver {

    public String hello(final String who) {
        return String.format("Hello, %s!", Optional.ofNullable(who).orElse("GraphQL"));
    }
}
