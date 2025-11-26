package com.constructrack.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Filtro JWT - Intercepta todas las solicitudes y valida el token JWT
 * Se ejecuta una sola vez por solicitud HTTP
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = obtenerJwtDelRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenProvider.validarToken(jwt)) {
                String nombreUsuario = jwtTokenProvider.obtenerNombreUsuario(jwt);
                String rol = jwtTokenProvider.obtenerRol(jwt);

                // Crear autenticación y establecerla en el contexto de seguridad
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                            nombreUsuario, null, new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("Autenticación establecida para usuario: {}", nombreUsuario);
            }
        } catch (Exception ex) {
            log.error("No se pudo establecer autenticación del usuario", ex);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT del encabezado Authorization de la solicitud
     *
     * @param request la solicitud HTTP
     * @return el token JWT si existe, null si no
     */
    private String obtenerJwtDelRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
