package com.api.profile.users.infrastructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

/**
 * OpenApi configuration.
 */
@OpenAPIDefinition(
    info = @Info(
        title = "Api Profile",
        description = "Manage user operations and creates token jwt",
        version = "1",
        contact = @Contact(
            name = "JEKA",
            email = "darwingranados54@gmail.com"
        )
    ),
    servers = {
        @Server(
            description = "LOCAL",
            url = "http://localhost:8080"
        ),
        @Server(
            description = "PROD",
            url = "https://prod:port"
        )
    },
    security = @SecurityRequirement(
        name = "Security Token"
    )
)
@SecurityScheme(
    name = "Security Token",
    description = "Access Token",
    type = SecuritySchemeType.HTTP,
    paramName = HttpHeaders.AUTHORIZATION,
    in = SecuritySchemeIn.HEADER,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {

}