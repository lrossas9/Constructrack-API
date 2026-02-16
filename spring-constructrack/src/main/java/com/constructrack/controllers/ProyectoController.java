
package com.constructrack.controllers;

import org.springframework.security.access.prepost.PreAuthorize;

import com.constructrack.dtos.ApiResponseDTO;
import com.constructrack.dtos.AvanceProyectoDTO;
import com.constructrack.dtos.CrearActividadDTO;
import com.constructrack.dtos.CrearProyectoDTO;
import com.constructrack.entities.Actividad;
import com.constructrack.entities.Proyecto;
import com.constructrack.services.ActividadService;
import com.constructrack.services.ProyectoService;
import com.constructrack.services.SeguimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * Controlador de Proyectos - Gestiona operaciones CRUD de proyectos y
 * actividades
 * 
 * Endpoints:
 * - POST /api/proyectos (RF01)
 * - GET /api/proyectos
 * - GET /api/proyectos/{id}/avance (RF02)
 * - POST /api/proyectos/{id}/actividades
 */
@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Proyectos", description = "Endpoints para gestionar proyectos y actividades")
@SecurityRequirement(name = "BearerAuth")
public class ProyectoController {

        private final ProyectoService proyectoService;
        private final ActividadService actividadService;
        private final SeguimientoService seguimientoService;

        /**
         * Crea un nuevo proyecto
         * Implementa RF01 - Registro de nuevo proyecto
         * Optimizado para respuesta menor a 3 segundos (RNF01)
         * 
         * @param crearProyectoDTO datos del proyecto
         * @return proyecto creado
         */
        @PostMapping
        @PreAuthorize("hasRole('ADMINISTRADOR_SISTEMA')")
        @Operation(summary = "Crear nuevo proyecto", description = "Crea un nuevo proyecto de construcción (Implementa RF01)", responses = {
                        @ApiResponse(responseCode = "201", description = "Proyecto creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                        @ApiResponse(responseCode = "401", description = "No autenticado")
        })
        public ResponseEntity<ApiResponseDTO<Proyecto>> crearProyecto(
                        @Valid @RequestBody CrearProyectoDTO crearProyectoDTO) {
                log.info("Creando nuevo proyecto: {}", crearProyectoDTO.getNombre());

                try {
                        Proyecto proyectoCreado = proyectoService.crearProyecto(crearProyectoDTO);

                        return ResponseEntity.status(HttpStatus.CREATED)
                                        .body(ApiResponseDTO.<Proyecto>builder()
                                                        .exito(true)
                                                        .mensaje("Proyecto creado exitosamente")
                                                        .datos(proyectoCreado)
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(ApiResponseDTO.<Proyecto>builder()
                                                        .exito(false)
                                                        .mensaje(e.getMessage())
                                                        .codigoError("PRY_001")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                }
        }

        /**
         * Obtiene todos los proyectos
         * Optimizado para rendimiento (RNF01)
         * 
         * @return lista de proyectos
         */
        @GetMapping
        @Operation(summary = "Obtener todos los proyectos", description = "Retorna la lista completa de proyectos", responses = {
                        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
                        @ApiResponse(responseCode = "401", description = "No autenticado")
        })
        public ResponseEntity<ApiResponseDTO<List<Proyecto>>> obtenerTodos() {
                try {
                        List<Proyecto> proyectos = proyectoService.obtenerTodosProyectos();

                        return ResponseEntity.ok(ApiResponseDTO.<List<Proyecto>>builder()
                                        .exito(true)
                                        .mensaje("Proyectos obtenidos exitosamente")
                                        .datos(proyectos)
                                        .timestamp(LocalDateTime.now())
                                        .build());
                } catch (Exception e) {
                        log.error("Error al obtener proyectos", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(ApiResponseDTO.<List<Proyecto>>builder()
                                                        .exito(false)
                                                        .mensaje("Error interno del servidor")
                                                        .codigoError("PRY_500")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                }
        }

        /**
         * Obtiene un proyecto por su ID
         * 
         * @param id ID del proyecto
         * @return proyecto solicitado
         */
        @GetMapping("/{id}")
        @Operation(summary = "Obtener proyecto por ID", description = "Retorna los detalles de un proyecto específico", responses = {
                        @ApiResponse(responseCode = "200", description = "Proyecto obtenido exitosamente"),
                        @ApiResponse(responseCode = "401", description = "No autenticado"),
                        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
        })
        public ResponseEntity<ApiResponseDTO<Proyecto>> obtenerPorId(@PathVariable Long id) {
                try {
                        Proyecto proyecto = proyectoService.obtenerProyectoPorId(id);

                        return ResponseEntity.ok(ApiResponseDTO.<Proyecto>builder()
                                        .exito(true)
                                        .mensaje("Proyecto obtenido exitosamente")
                                        .datos(proyecto)
                                        .timestamp(LocalDateTime.now())
                                        .build());
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ApiResponseDTO.<Proyecto>builder()
                                                        .exito(false)
                                                        .mensaje(e.getMessage())
                                                        .codigoError("PRY_404")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                }
        }

        /**
         * Obtiene el avance actual de un proyecto
         * Implementa RF02 - Consulta de avance en tiempo real
         * 
         * @param id ID del proyecto
         * @return DTO con información del avance
         */
        @GetMapping("/{id}/avance")
        @Operation(summary = "Obtener avance del proyecto", description = "Retorna el avance actual del proyecto en tiempo real (Implementa RF02)", responses = {
                        @ApiResponse(responseCode = "200", description = "Avance obtenido exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponseDTO.class))),
                        @ApiResponse(responseCode = "401", description = "No autenticado"),
                        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
        })
        public ResponseEntity<ApiResponseDTO<AvanceProyectoDTO>> obtenerAvance(@PathVariable Long id) {
                try {
                        AvanceProyectoDTO avance = seguimientoService.obtenerAvanceActual(id);

                        return ResponseEntity.ok(ApiResponseDTO.<AvanceProyectoDTO>builder()
                                        .exito(true)
                                        .mensaje("Avance obtenido exitosamente")
                                        .datos(avance)
                                        .timestamp(LocalDateTime.now())
                                        .build());
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ApiResponseDTO.<AvanceProyectoDTO>builder()
                                                        .exito(false)
                                                        .mensaje(e.getMessage())
                                                        .codigoError("PRY_404")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                }
        }

        /**
         * Crea una nueva actividad para un proyecto
         * 
         * @param idProyecto        ID del proyecto
         * @param crearActividadDTO datos de la actividad
         * @return actividad creada
         */
        @PostMapping("/{idProyecto}/actividades")
        @Operation(summary = "Crear actividad", description = "Crea una nueva actividad asociada a un proyecto", responses = {
                        @ApiResponse(responseCode = "201", description = "Actividad creada exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                        @ApiResponse(responseCode = "401", description = "No autenticado"),
                        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
        })
        public ResponseEntity<ApiResponseDTO<Actividad>> crearActividad(
                        @PathVariable Long idProyecto,
                        @Valid @RequestBody CrearActividadDTO crearActividadDTO) {
                log.info("Creando nueva actividad para proyecto: {}", idProyecto);

                try {
                        Actividad actividadCreada = actividadService.crearActividad(idProyecto, crearActividadDTO);

                        return ResponseEntity.status(HttpStatus.CREATED)
                                        .body(ApiResponseDTO.<Actividad>builder()
                                                        .exito(true)
                                                        .mensaje("Actividad creada exitosamente")
                                                        .datos(actividadCreada)
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ApiResponseDTO.<Actividad>builder()
                                                        .exito(false)
                                                        .mensaje(e.getMessage())
                                                        .codigoError("ACT_404")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                }
        }

        /**
         * Obtiene todas las actividades de un proyecto
         * 
         * @param idProyecto ID del proyecto
         * @return lista de actividades
         */
        @GetMapping("/{idProyecto}/actividades")
        @Operation(summary = "Obtener actividades del proyecto", description = "Retorna todas las actividades asociadas a un proyecto", responses = {
                        @ApiResponse(responseCode = "200", description = "Actividades obtenidas exitosamente"),
                        @ApiResponse(responseCode = "401", description = "No autenticado"),
                        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
        })
        public ResponseEntity<ApiResponseDTO<List<Actividad>>> obtenerActividades(@PathVariable Long idProyecto) {
                try {
                        List<Actividad> actividades = actividadService.obtenerActividadesPorProyecto(idProyecto);

                        return ResponseEntity.ok(ApiResponseDTO.<List<Actividad>>builder()
                                        .exito(true)
                                        .mensaje("Actividades obtenidas exitosamente")
                                        .datos(actividades)
                                        .timestamp(LocalDateTime.now())
                                        .build());
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ApiResponseDTO.<List<Actividad>>builder()
                                                        .exito(false)
                                                        .mensaje(e.getMessage())
                                                        .codigoError("ACT_404")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                }
        }

        /**
         * Actualiza un proyecto
         * 
         * @param id               ID del proyecto
         * @param crearProyectoDTO nuevos datos
         * @return proyecto actualizado
         */
        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMINISTRADOR_SISTEMA')")
        @Operation(summary = "Actualizar proyecto", description = "Actualiza los datos de un proyecto", responses = {
                        @ApiResponse(responseCode = "200", description = "Proyecto actualizado exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                        @ApiResponse(responseCode = "401", description = "No autenticado"),
                        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
        })
        public ResponseEntity<ApiResponseDTO<Proyecto>> actualizar(
                        @PathVariable Long id,
                        @Valid @RequestBody CrearProyectoDTO crearProyectoDTO) {
                try {
                        Proyecto proyectoActualizado = proyectoService.actualizarProyecto(id, crearProyectoDTO);

                        return ResponseEntity.ok(ApiResponseDTO.<Proyecto>builder()
                                        .exito(true)
                                        .mensaje("Proyecto actualizado exitosamente")
                                        .datos(proyectoActualizado)
                                        .timestamp(LocalDateTime.now())
                                        .build());
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ApiResponseDTO.<Proyecto>builder()
                                                        .exito(false)
                                                        .mensaje(e.getMessage())
                                                        .codigoError("PRY_404")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                }
        }

        /**
         * Elimina un proyecto
         * 
         * @param id ID del proyecto
         */
        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMINISTRADOR_SISTEMA')")
        @Operation(summary = "Eliminar proyecto", description = "Elimina un proyecto y todas sus actividades asociadas", responses = {
                        @ApiResponse(responseCode = "200", description = "Proyecto eliminado exitosamente"),
                        @ApiResponse(responseCode = "401", description = "No autenticado"),
                        @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
        })
        public ResponseEntity<ApiResponseDTO<?>> eliminar(@PathVariable Long id) {
                try {
                        proyectoService.eliminarProyecto(id);

                        return ResponseEntity.ok(ApiResponseDTO.builder()
                                        .exito(true)
                                        .mensaje("Proyecto eliminado exitosamente")
                                        .timestamp(LocalDateTime.now())
                                        .build());
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ApiResponseDTO.builder()
                                                        .exito(false)
                                                        .mensaje(e.getMessage())
                                                        .codigoError("PRY_404")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                }
        }
}
