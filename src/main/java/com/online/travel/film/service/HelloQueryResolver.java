package com.online.travel.film.service;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Service;

@Service
public class HelloQueryResolver implements GraphQLQueryResolver {

    public String hello() {
        return "Hello, GraphQL!";
    }
}
