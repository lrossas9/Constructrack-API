package com.constructrack.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para registro de nuevo usuario
 * Documentado para Swagger
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    private String contrasena;

    @NotBlank(message = "El rol es obligatorio")
    private String rol; // ADMINISTRADOR_OBRA, RESIDENTE_OBRA, SUPERVISOR, TRABAJADOR

    private String nombre;

    private String apellido;

    private String telefono;
}
