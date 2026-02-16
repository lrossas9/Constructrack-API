package com.constructrack.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PresupuestoItemDTO {
    private Long idItem;

    @NotBlank(message = "El nombre del ítem es obligatorio")
    private String item;

    private String descripcion;

    @NotBlank(message = "La unidad es obligatoria")
    private String unidad;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0")
    private Double cantidad;

    @NotNull(message = "El valor unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El valor unitario debe ser mayor a 0")
    private Double valorUnitario;
}
