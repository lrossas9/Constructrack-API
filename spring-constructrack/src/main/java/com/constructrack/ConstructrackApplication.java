package com.constructrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase principal de la aplicación Constructrack API
 * Configura la aplicación Spring Boot y define beans globales
 */
@SpringBootApplication
public class ConstructrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConstructrackApplication.class, args);
    }

    /**
     * Define el encodificador de contraseñas usando BCrypt
     * Cumple con RNF de seguridad robusta
     *
     * @return PasswordEncoder configurado con BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
