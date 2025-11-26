package com.constructrack.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de autenticación exitosa
 * Contiene el token JWT para futuras solicitudes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private Long idUsuario;

    private String nombreUsuario;

    private String correo;

    private String rol;

    private String token;

    private LocalDateTime fechaExpiracion;

    private String mensaje;
}
