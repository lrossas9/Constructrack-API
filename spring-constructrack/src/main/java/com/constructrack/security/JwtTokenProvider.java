package com.constructrack.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Proveedor de tokens JWT (JSON Web Tokens)
 * Genera y valida tokens para autenticación
 * Cumple con RNF de seguridad robusta mediante JWT
 */
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${app.jwt.secret:ConstructrackSecretKeyMustBeAtLeast256BitsLongForHS256SecurityAlgorithmToWork2024}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms:86400000}") // 24 horas por defecto
    private long jwtExpirationMs;

    /**
     * Genera un token JWT para un usuario
     *
     * @param idUsuario ID del usuario
     * @param nombreUsuario nombre del usuario
     * @param rol rol del usuario
     * @return token JWT generado
     */
    public String generarToken(Long idUsuario, String nombreUsuario, String rol) {
        log.debug("Generando token JWT para usuario: {}", idUsuario);

        Map<String, Object> claims = new HashMap<>();
        claims.put("idUsuario", idUsuario);
        claims.put("rol", rol);

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime expiracion = ahora.plusSeconds(jwtExpirationMs / 1000);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(nombreUsuario)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();

        log.debug("Token generado exitosamente para usuario: {}", idUsuario);
        return token;
    }

    /**
     * Extrae el nombre de usuario del token JWT
     *
     * @param token el token JWT
     * @return nombre de usuario extraído
     */
    public String obtenerNombreUsuario(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Extrae el ID del usuario del token JWT
     *
     * @param token el token JWT
     * @return ID del usuario extraído
     */
    public Long obtenerIdUsuario(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("idUsuario", Long.class);
    }

    /**
     * Valida que un token JWT sea válido
     *
     * @param token el token JWT a validar
     * @return true si el token es válido, false si no
     */
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            log.debug("Token válido");
            return true;
        } catch (Exception e) {
            log.warn("Token JWT inválido: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el rol del usuario desde el token
     *
     * @param token el token JWT
     * @return rol del usuario
     */
    public String obtenerRol(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("rol", String.class);
    }
}
