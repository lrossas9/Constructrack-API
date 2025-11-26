package com.constructrack.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para crear una nueva actividad en un proyecto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearActividadDTO {

    @NotBlank(message = "El nombre de la actividad es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    private String estado;

    private Integer porcentajeAvance;

    private String responsable;

    private Double presupuestoActividad;
}
