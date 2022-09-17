package com.vsu.myblog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Configuration
public class SpringDocConfig {

    @Value("${swagger.url}")
    private String url;

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl(url);
        final String securitySchemeName = "Bearer Authentication";
        return new OpenAPI().info(new Info().title(title).description(description)).servers(List.of(server))
                .components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("A JWT access-token in header Authorization is required to access this API. " +
                                "JWT token can be obtained by providing correct username and password in the User API")
                )).security(List.of(new SecurityRequirement().addList(securitySchemeName)));
    }
}
