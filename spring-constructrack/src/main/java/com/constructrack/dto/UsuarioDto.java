package com.constructrack.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class UsuarioDto {
    private Long idUsuario;

    @NotBlank
    private String nombre;

    @Email
    @NotBlank
    private String correo;

    @NotBlank
    @Size(min = 8)
    private String contraseña;

    @NotBlank
    private String rol;
}
