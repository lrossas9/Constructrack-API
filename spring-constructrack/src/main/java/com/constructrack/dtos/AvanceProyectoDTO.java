package com.constructrack.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para respuesta de consulta de avance de obra
 * Implementa RF02 - Consulta de avance en tiempo real
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvanceProyectoDTO {

    private Long idProyecto;

    private String nombreProyecto;

    private BigDecimal porcentajeAvance;

    private LocalDate ultimaActualizacion;

    private String estado;

    private String observaciones;

    private String ubicacion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;
}
