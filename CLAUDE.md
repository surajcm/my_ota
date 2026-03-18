# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3.4.1 application that provides an Online Travel Agent (OTA) service connecting to IATA NDC sandbox APIs. The application exposes both REST and GraphQL endpoints for flight shopping and offers.

**Tech Stack:** Java 17, Spring Boot 3.4.1, Maven, JAXB, JUnit 5, Mockito

## Build & Run Commands

### Build
```bash
./mvnw clean install
```

### Run Application
```bash
./mvnw spring-boot:run
```

If you have a custom `~/.m2/settings.xml` pointing to an organization's Maven repo:
```bash
./mvnw -s settings.xml spring-boot:run
```

The application runs on **port 8090** with remote debugging enabled on port 5005.

### Testing
```bash
# Run all tests
./mvnw test

# Run tests with coverage
./mvnw clean verify

# Run a specific test class
./mvnw test -Dtest=AirControllerTest

# Run a specific test method
./mvnw test -Dtest=AirControllerTest#flightSearch
```

### Code Quality
```bash
# Run Checkstyle
./mvnw checkstyle:check

# Run PMD
./mvnw pmd:check

# Run CPD (copy-paste detection)
./mvnw pmd:cpd-check

# Generate JaCoCo coverage report
./mvnw jacoco:report
```
Coverage reports are available at `target/site/jacoco/index.html`.

## Architecture

The application follows a layered architecture for processing flight shopping requests:

### Request Flow
1. **Controller** (`air.controller`) - REST endpoints receive requests as query parameters
2. **Builder** (`air.builder`) - Converts query parameters into internal request models
3. **Validator** (`air.validator`) - Validates the request models
4. **Service** (`air.service`) - Business logic layer, orchestrates the flow
5. **Mapper** (`air.mapper`) - Transforms internal models to/from IATA NDC XML schema models
6. **Connector** (`air.connector`, `dataaccess.connector`) - HTTP communication with IATA NDC API
7. **Mapper** (response) - Transforms IATA NDC responses back to internal models
8. **Controller** (response) - Returns JSON responses to clients

### Key Packages

- `com.online.travel.air.*` - Air travel domain logic (shop, offers)
- `com.online.travel.model.*` - Internal request/response models
- `com.online.travel.schema.*` - **Auto-generated JAXB classes from XSD schemas** (not in git)
- `com.online.travel.dataaccess.*` - Data access utilities (REST connector, serialization)
- `com.online.travel.graphql.*` - GraphQL resolvers (currently minimal implementation)

### Schema Generation

JAXB classes in `com.online.travel.schema.*` are **generated at build time** from XSD files in `src/main/xsd/`. These directories are gitignored. If you see compilation errors related to schema classes, run a build first to generate them:

```bash
./mvnw clean compile
```

The `jaxb-maven-plugin` generates:
- `schema.request.shop` from `IATA_AirShoppingRQ.xsd`
- `schema.response.shop` from `IATA_AirShoppingRS.xsd`
- `schema.request.offer` from `IATA_OfferPriceRQ.xsd`
- `schema.response.offer` from `IATA_OfferPriceRS.xsd`
- `schema.error` from `error.xsd`

## API Endpoints

### REST APIs
- **Flight Search**: `GET /air/listings/?origin=JFK&destination=LAX&departure=2024-12-01&...`
- **Flight Offers (Re-pricing)**: `GET /air/offers/?...`

**Swagger UI**: http://localhost:8090/swagger-ui/index.html

### GraphQL
- **Endpoint**: http://localhost:8090/graphql
- **GraphiQL UI**: http://localhost:8090/graphiql

The GraphQL implementation is currently minimal (only a hello query). Real flight operations use REST endpoints.

### External API

The application connects to IATA NDC sandbox API at `http://iata.api.mashery.com/athena/ndc192api`. Configuration is in `application.yml` under `air.shop.url` and `air.offer.url`.

## Development Patterns

### Test Configuration
- Tests use `testFailureIgnore: true` in surefire configuration, so builds don't fail on test failures
- Test-specific configurations are in `src/test/java/.../config/`
- Schema classes are excluded from JaCoCo coverage

### Logging
- Application logs to `logs/MyOtaApp.log`
- Log history is cleaned on application start
- Default log level: INFO for `com.online.travel` package

### Code Style
- Checkstyle rules in `config/checkstyle/checkstyle.xml`
- PMD rules in `config/pmd/ruleset.xml`
- Suppressions configured in respective config files
- Java 17 enforced via maven-enforcer-plugin

## Important Notes

- The project requires Java 17 (enforced during compile phase)
- Maven wrapper (`mvnw`) is committed to the repository
- When modifying XSD schemas, re-run the build to regenerate JAXB classes
- The application uses Jakarta XML Binding (formerly javax.xml.bind) - this is the Jakarta EE migration
