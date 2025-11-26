package com.constructrack.dtos;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar perfil de usuario
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarUsuarioDTO {

    private String nombre;

    private String apellido;

    @Email(message = "El correo debe ser válido")
    private String correo;

    private String telefono;

    private String rol;
}
