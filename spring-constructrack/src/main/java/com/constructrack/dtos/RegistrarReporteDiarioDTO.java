package com.constructrack.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para registrar reporte diario de obra
 * Incluye clima, observaciones, trabajadores y novedades
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarReporteDiarioDTO {

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "La información del clima es obligatoria")
    private String clima;

    @NotNull(message = "El ID del proyecto es obligatorio")
    private Long idProyecto;

    private Long idActividad;

    private String observaciones;

    private Integer cantidadTrabajadores;

    private Double horasTrabajadas;

    private String novedades;

    private String incidentes;

    private String materialesUtilizados;
}
