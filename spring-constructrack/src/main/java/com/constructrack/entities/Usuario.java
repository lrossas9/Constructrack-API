package com.constructrack.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad Usuario - Gestiona información de usuarios del sistema
 * Cumple con requisitos de autenticación y seguridad del proyecto
 *
 * Requisitos:
 * - RF: Autenticación y registro de usuarios
 * - RNF: Contraseñas hasheadas con BCrypt
 * - Roles: Administrador de obra, Residente de obra, etc.
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Column(nullable = false, unique = true, length = 50)
    private String nombreUsuario;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    /**
     * Contraseña hasheada con BCrypt
     * Nunca se almacena en texto plano
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false, length = 255)
    private String contrasena;

    @NotBlank(message = "El rol es obligatorio")
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private RolEnum rol;

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String apellido;

    @Column(length = 20)
    private String telefono;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    /**
     * Enumeración de roles disponibles en el sistema
     */
    public enum RolEnum {
        ADMINISTRADOR_OBRA,      // Administrador general de la obra
        RESIDENTE_OBRA,          // Residente encargado de la obra
        SUPERVISOR,              // Supervisor de actividades
        TRABAJADOR,              // Trabajador general
        ADMINISTRADOR_SISTEMA    // Administrador del sistema
    }
}
