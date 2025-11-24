package com.constructrack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Constructrack API - Spring Boot")
                        .version("1.0.0")
                        .description("API REST para gestión de proyectos, actividades y reportes")
                        .license(new License().name("MIT")));
    }
}
