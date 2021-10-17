package com.online.travel.init;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class Initializer {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(200))
                .setReadTimeout(Duration.ofSeconds(200))
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") final String description,
                                 @Value("${application-version}") final String appVersion) {

        return new OpenAPI()
                .info(new Info()
                        .title("Online Travel Agent API")
                        .version(appVersion)
                        .description(description)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org")));

    }

}
