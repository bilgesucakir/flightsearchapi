package com.bilgesucakir.flightsearchapi.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Swagger OpenAPI configuration class
 */
@OpenAPIDefinition(
        info = @Info(
             contact = @Contact(name = "Bilgesu Çakır", email = "bilgesucakir@sabanciuniv.edu"),
                description = "OpenAPI documentation for Flight Search API, a Spring Boot REST API as a case study for Amadeus Travel to Future Program",
                title = "Flight Search API",
                version = "1.0"
        ),
        externalDocs = @ExternalDocumentation(
                description = "Source code on GitHub",
                url = "https://github.com/bilgesucakir/flightsearchapi"
        ),
        servers = {@Server(description = "Local ENV", url = "http://localhost:8080")}

)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerOpenAPIConfig {
}
