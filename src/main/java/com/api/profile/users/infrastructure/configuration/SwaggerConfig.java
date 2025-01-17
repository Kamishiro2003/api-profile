package com.api.profile.users.infrastructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

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
            url = "https://3.83.39.17:8080"
        )
    }
)
public class SwaggerConfig {

}