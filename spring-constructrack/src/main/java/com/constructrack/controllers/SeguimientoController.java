package com.constructrack.controllers;

import com.constructrack.dtos.ApiResponseDTO;
import com.constructrack.dtos.RegistrarSeguimientoDTO;
import com.constructrack.entities.Seguimiento;
import com.constructrack.services.SeguimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador de Seguimiento - Gestiona el avance de obra
 * Implementa RF02 - Consulta de avance en tiempo real
 * 
 * Endpoint:
 * - POST /api/seguimiento
 */
@RestController
@RequestMapping("/api/seguimiento")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Seguimiento", description = "Endpoints para registrar y consultar avance de obra")
@SecurityRequirement(name = "BearerAuth")
public class SeguimientoController {

    private final SeguimientoService seguimientoService;

    /**
     * Registra un nuevo seguimiento de avance de obra
     * Implementa RF02 - Con tipo DECIMAL(3,2) para avance_porcentaje
     * 
     * @param registrarSeguimientoDTO datos del seguimiento
     * @return seguimiento registrado
     */
    @PostMapping
    @Operation(
            summary = "Registrar seguimiento de obra",
            description = "Registra un nuevo seguimiento de avance con porcentaje en rango 0-100",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Seguimiento registrado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "401", description = "No autenticado"),
                    @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
            }
    )
    public ResponseEntity<ApiResponseDTO<Seguimiento>> registrarSeguimiento(
            @Valid @RequestBody RegistrarSeguimientoDTO registrarSeguimientoDTO) {
        log.info("Registrando seguimiento para proyecto: {}", registrarSeguimientoDTO.getIdProyecto());

        try {
            Seguimiento seguimientoCreado = seguimientoService.registrarSeguimiento(registrarSeguimientoDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.<Seguimiento>builder()
                            .exito(true)
                            .mensaje("Seguimiento registrado exitosamente")
                            .datos(seguimientoCreado)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.<Seguimiento>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("SEG_001")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Obtiene todos los seguimientos de un proyecto
     * 
     * @param idProyecto ID del proyecto
     * @return lista de seguimientos
     */
    @GetMapping("/proyecto/{idProyecto}")
    @Operation(
            summary = "Obtener seguimientos de un proyecto",
            description = "Retorna todos los registros de seguimiento de un proyecto ordenados por fecha",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Seguimientos obtenidos exitosamente"),
                    @ApiResponse(responseCode = "401", description = "No autenticado"),
                    @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
            }
    )
    public ResponseEntity<ApiResponseDTO<List<Seguimiento>>> obtenerPorProyecto(@PathVariable Long idProyecto) {
        try {
            List<Seguimiento> seguimientos = seguimientoService.obtenerSeguimientosPorProyecto(idProyecto);

            return ResponseEntity.ok(ApiResponseDTO.<List<Seguimiento>>builder()
                    .exito(true)
                    .mensaje("Seguimientos obtenidos exitosamente")
                    .datos(seguimientos)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.<List<Seguimiento>>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("SEG_404")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Obtiene un seguimiento por su ID
     * 
     * @param id ID del seguimiento
     * @return seguimiento solicitado
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener seguimiento por ID",
            description = "Retorna los detalles de un seguimiento específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Seguimiento obtenido exitosamente"),
                    @ApiResponse(responseCode = "401", description = "No autenticado"),
                    @ApiResponse(responseCode = "404", description = "Seguimiento no encontrado")
            }
    )
    public ResponseEntity<ApiResponseDTO<Seguimiento>> obtenerPorId(@PathVariable Long id) {
        try {
            Seguimiento seguimiento = seguimientoService.obtenerSeguimientoPorId(id);

            return ResponseEntity.ok(ApiResponseDTO.<Seguimiento>builder()
                    .exito(true)
                    .mensaje("Seguimiento obtenido exitosamente")
                    .datos(seguimiento)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.<Seguimiento>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("SEG_404")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Actualiza un seguimiento
     * 
     * @param id ID del seguimiento
     * @param registrarSeguimientoDTO nuevos datos
     * @return seguimiento actualizado
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar seguimiento",
            description = "Actualiza los datos de un seguimiento registrado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Seguimiento actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "401", description = "No autenticado"),
                    @ApiResponse(responseCode = "404", description = "Seguimiento no encontrado")
            }
    )
    public ResponseEntity<ApiResponseDTO<Seguimiento>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RegistrarSeguimientoDTO registrarSeguimientoDTO) {
        try {
            Seguimiento seguimientoActualizado = seguimientoService.actualizarSeguimiento(id, registrarSeguimientoDTO);

            return ResponseEntity.ok(ApiResponseDTO.<Seguimiento>builder()
                    .exito(true)
                    .mensaje("Seguimiento actualizado exitosamente")
                    .datos(seguimientoActualizado)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.<Seguimiento>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("SEG_404")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Elimina un seguimiento
     * 
     * @param id ID del seguimiento a eliminar
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar seguimiento",
            description = "Elimina un registro de seguimiento",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Seguimiento eliminado exitosamente"),
                    @ApiResponse(responseCode = "401", description = "No autenticado"),
                    @ApiResponse(responseCode = "404", description = "Seguimiento no encontrado")
            }
    )
    public ResponseEntity<ApiResponseDTO<?>> eliminar(@PathVariable Long id) {
        try {
            seguimientoService.eliminarSeguimiento(id);

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .exito(true)
                    .mensaje("Seguimiento eliminado exitosamente")
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("SEG_404")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }
}
