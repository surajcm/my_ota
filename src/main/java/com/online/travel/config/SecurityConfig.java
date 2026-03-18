package com.online.travel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Security configuration for the application.
 * Adds security headers and CORS configuration.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configure CORS for the application.
     * In production, replace "*" with specific allowed origins.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                configureCors(registry, "/air/**");
                configureCors(registry, "/graphql");
            }
        };
    }

    private void configureCors(final CorsRegistry registry, final String path) {
        registry.addMapping(path)
                .allowedOrigins("*") // TODO: Replace with specific origins in production
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
