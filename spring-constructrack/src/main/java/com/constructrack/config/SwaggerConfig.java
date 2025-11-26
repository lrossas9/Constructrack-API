package com.constructrack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para documentación de APIs
 * Cumple con requisitos de documentación del proyecto
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Constructrack API")
                        .version("1.0.0")
                        .description("API Backend para la gestión de proyectos de construcción\n\n" +
                                "Esta API proporciona endpoints para:\n" +
                                "- Autenticación de usuarios con JWT\n" +
                                "- Gestión de proyectos de construcción\n" +
                                "- Registro de actividades y seguimiento de avance\n" +
                                "- Reportes diarios de obra\n" +
                                "- Carga de evidencias (fotos/archivos)\n\n" +
                                "Todos los endpoints excepto /api/auth/** y /api/usuarios/registro requieren autenticación mediante token JWT")
                        .contact(new Contact()
                                .name("Equipo Constructrack")
                                .email("info@constructrack.com")))
                .components(new Components()
                        .addSecuritySchemes("BearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Token JWT. Copiar desde la respuesta del login y pegar aquí con el prefijo 'Bearer '"))
                )
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
    }
}
