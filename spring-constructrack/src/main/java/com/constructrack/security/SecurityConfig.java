package com.constructrack.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad Spring Security
 * Define qué endpoints requieren autenticación y cuáles no
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        // Endpoints públicos (sin autenticación)
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/usuarios/registro").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        
                        // Endpoints que requieren autenticación
                        .requestMatchers(HttpMethod.GET, "/api/proyectos/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/proyectos/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/proyectos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/proyectos/**").authenticated()
                        
                        .requestMatchers("/api/usuarios/**").authenticated()
                        .requestMatchers("/api/seguimiento/**").authenticated()
                        .requestMatchers("/api/reportes/**").authenticated()
                        
                        // Todos los demás requieren autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
