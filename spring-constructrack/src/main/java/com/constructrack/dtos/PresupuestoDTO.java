package com.constructrack.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class PresupuestoDTO {
    private Long idPresupuesto;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El id del usuario creador es obligatorio")
    private Long idUsuarioCreador;

    @NotNull(message = "El id del proyecto es obligatorio")
    private Long idProyecto;

    @NotNull(message = "Debe incluir al menos un ítem")
    private List<PresupuestoItemDTO> items;
}
