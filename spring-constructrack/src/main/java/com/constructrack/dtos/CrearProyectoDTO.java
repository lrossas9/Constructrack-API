package com.constructrack.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para crear un nuevo proyecto
 * Implementa RF01 - Registro de nuevo proyecto
 * Documentado para Swagger
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearProyectoDTO {

    @NotBlank(message = "El nombre del proyecto es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    private String estado;

    private Double presupuesto;

    private String cliente;

    private String contratista;
}
