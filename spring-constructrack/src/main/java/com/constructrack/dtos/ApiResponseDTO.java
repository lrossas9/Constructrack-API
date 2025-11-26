package com.constructrack.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO genérico para respuestas de la API
 * Se utiliza para estandarizar todas las respuestas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDTO<T> {

    private boolean exito;

    private String mensaje;

    private T datos;

    private String codigoError;

    private LocalDateTime timestamp;
}
