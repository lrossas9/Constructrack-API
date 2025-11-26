package com.constructrack.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para registrar avance de obra
 * Implementa RF02 - Registro del avance con tipo DECIMAL(3,2)
 * Rango permitido: 0.00 a 100.00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarSeguimientoDTO {

    @NotNull(message = "El ID del proyecto es obligatorio")
    private Long idProyecto;

    @NotNull(message = "El porcentaje de avance es obligatorio")
    @DecimalMin(value = "0.0", message = "El avance debe ser mayor o igual a 0")
    @DecimalMax(value = "100.0", message = "El avance no puede exceder 100")
    private BigDecimal avancePorcentaje;

    @NotNull(message = "La fecha de seguimiento es obligatoria")
    private LocalDate fechaSeguimiento;

    private String observaciones;

    private String estado;
}
