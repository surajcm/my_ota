package com.online.travel.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filter to add security headers to all HTTP responses.
 */
@Component
public class SecurityHeadersFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                        final FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Prevent clickjacking attacks
        httpResponse.setHeader("X-Frame-Options", "DENY");

        // Prevent MIME type sniffing
        httpResponse.setHeader("X-Content-Type-Options", "nosniff");

        // Enable XSS protection
        httpResponse.setHeader("X-XSS-Protection", "1; mode=block");

        // Content Security Policy
        httpResponse.setHeader("Content-Security-Policy",
                "default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'");

        // Referrer Policy
        httpResponse.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");

        // Permissions Policy (formerly Feature Policy)
        httpResponse.setHeader("Permissions-Policy", "geolocation=(), microphone=(), camera=()");

        chain.doFilter(request, response);
    }
}
