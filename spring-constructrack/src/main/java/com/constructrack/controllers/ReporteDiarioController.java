package com.constructrack.controllers;

import com.constructrack.dtos.ApiResponseDTO;
import com.constructrack.dtos.RegistrarReporteDiarioDTO;
import com.constructrack.entities.Evidencia;
import com.constructrack.entities.ReporteDiario;
import com.constructrack.security.JwtTokenProvider;
import com.constructrack.services.EvidenciaService;
import com.constructrack.services.ReporteDiarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Controlador de Reportes Diarios - Gestiona reportes y evidencias
 * 
 * Endpoints:
 * - POST /api/reportes/diarios
 * - POST /api/reportes/evidencias
 */
@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reportes", description = "Endpoints para gestionar reportes diarios y evidencias")
@SecurityRequirement(name = "BearerAuth")
public class ReporteDiarioController {

    private final ReporteDiarioService reporteDiarioService;
    private final EvidenciaService evidenciaService;
    private final JwtTokenProvider jwtTokenProvider;
    
    private static final String DIRECTORIO_EVIDENCIAS = "uploads/evidencias/";

    /**
     * Registra un nuevo reporte diario de obra
     * 
     * @param registrarReporteDiarioDTO datos del reporte
     * @param request solicitud HTTP (para extraer token)
     * @return reporte registrado
     */
    @PostMapping("/diarios")
    @Operation(
            summary = "Registrar reporte diario",
            description = "Registra un nuevo informe diario de la obra",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reporte registrado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "401", description = "No autenticado"),
                    @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
            }
    )
    public ResponseEntity<ApiResponseDTO<ReporteDiario>> registrarReporteDiario(
            @Valid @RequestBody RegistrarReporteDiarioDTO registrarReporteDiarioDTO,
            HttpServletRequest request) {
        log.info("Registrando reporte diario para proyecto: {}", registrarReporteDiarioDTO.getIdProyecto());

        try {
            // Extraer ID del usuario desde el token JWT
            Long idUsuario = extraerIdUsuarioDelToken(request);

            ReporteDiario reporteCreado = reporteDiarioService.registrarReporteDiario(registrarReporteDiarioDTO, idUsuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.<ReporteDiario>builder()
                            .exito(true)
                            .mensaje("Reporte diario registrado exitosamente")
                            .datos(reporteCreado)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.<ReporteDiario>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("REP_404")
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error al registrar reporte diario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.<ReporteDiario>builder()
                            .exito(false)
                            .mensaje("Error interno del servidor")
                            .codigoError("REP_500")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Carga una evidencia (foto/archivo) para un reporte
     * 
     * @param idReporteDiario ID del reporte
     * @param archivo archivo a cargar
     * @param tipoArchivo tipo de archivo (FOTO, DOCUMENTO, VIDEO, OTRO)
     * @param descripcion descripción de la evidencia
     * @return evidencia registrada
     */
    @PostMapping("/evidencias")
    @Operation(
            summary = "Cargar evidencia",
            description = "Carga un archivo o foto como evidencia de un reporte",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Evidencia cargada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "401", description = "No autenticado"),
                    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
            }
    )
    public ResponseEntity<ApiResponseDTO<Evidencia>> cargarEvidencia(
            @RequestParam("idReporteDiario") Long idReporteDiario,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("tipoArchivo") String tipoArchivo,
            @RequestParam("descripcion") String descripcion) {
        log.info("Cargando evidencia para reporte: {}", idReporteDiario);

        try {
            // Validar que el archivo no sea vacío
            if (archivo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponseDTO.<Evidencia>builder()
                                .exito(false)
                                .mensaje("El archivo no puede estar vacío")
                                .codigoError("EVD_001")
                                .timestamp(LocalDateTime.now())
                                .build());
            }

            // Generar nombre único para el archivo
            String nombreOriginal = archivo.getOriginalFilename();
            String nombreUnico = UUID.randomUUID().toString() + "_" + nombreOriginal;
            
            // Crear directorio si no existe
            Path directorioPath = Path.of(DIRECTORIO_EVIDENCIAS);
            Files.createDirectories(directorioPath);
            
            // Guardar archivo
            Path rutaArchivo = directorioPath.resolve(nombreUnico);
            Files.copy(archivo.getInputStream(), rutaArchivo);

            // Registrar evidencia en la base de datos
            Evidencia evidenciaCreada = evidenciaService.registrarEvidencia(
                    idReporteDiario,
                    nombreOriginal,
                    rutaArchivo.toString(),
                    tipoArchivo,
                    archivo.getSize(),
                    descripcion
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.<Evidencia>builder()
                            .exito(true)
                            .mensaje("Evidencia cargada exitosamente")
                            .datos(evidenciaCreada)
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (IOException e) {
            log.error("Error al cargar archivo", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.<Evidencia>builder()
                            .exito(false)
                            .mensaje("Error al cargar el archivo")
                            .codigoError("EVD_500")
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.<Evidencia>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("EVD_404")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Obtiene todos los reportes de un proyecto
     * 
     * @param idProyecto ID del proyecto
     * @return lista de reportes
     */
    @GetMapping("/proyecto/{idProyecto}")
    @Operation(
            summary = "Obtener reportes de un proyecto",
            description = "Retorna todos los reportes diarios de un proyecto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reportes obtenidos exitosamente"),
                    @ApiResponse(responseCode = "401", description = "No autenticado"),
                    @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
            }
    )
    public ResponseEntity<ApiResponseDTO<List<ReporteDiario>>> obtenerPorProyecto(@PathVariable Long idProyecto) {
        try {
            List<ReporteDiario> reportes = reporteDiarioService.obtenerReportesPorProyecto(idProyecto);

            return ResponseEntity.ok(ApiResponseDTO.<List<ReporteDiario>>builder()
                    .exito(true)
                    .mensaje("Reportes obtenidos exitosamente")
                    .datos(reportes)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.<List<ReporteDiario>>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("REP_404")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Obtiene un reporte por su ID
     * 
     * @param id ID del reporte
     * @return reporte solicitado
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener reporte por ID",
            description = "Retorna los detalles de un reporte específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reporte obtenido exitosamente"),
                    @ApiResponse(responseCode = "401", description = "No autenticado"),
                    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
            }
    )
    public ResponseEntity<ApiResponseDTO<ReporteDiario>> obtenerPorId(@PathVariable Long id) {
        try {
            ReporteDiario reporte = reporteDiarioService.obtenerReportePorId(id);

            return ResponseEntity.ok(ApiResponseDTO.<ReporteDiario>builder()
                    .exito(true)
                    .mensaje("Reporte obtenido exitosamente")
                    .datos(reporte)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.<ReporteDiario>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("REP_404")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Obtiene las evidencias de un reporte
     * 
     * @param idReporteDiario ID del reporte
     * @return lista de evidencias
     */
    @GetMapping("/{idReporteDiario}/evidencias")
    @Operation(
            summary = "Obtener evidencias de un reporte",
            description = "Retorna todas las evidencias asociadas a un reporte",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Evidencias obtenidas exitosamente"),
                    @ApiResponse(responseCode = "401", description = "No autenticado"),
                    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
            }
    )
    public ResponseEntity<ApiResponseDTO<List<Evidencia>>> obtenerEvidencias(@PathVariable Long idReporteDiario) {
        try {
            List<Evidencia> evidencias = evidenciaService.obtenerEvidenciasPorReporte(idReporteDiario);

            return ResponseEntity.ok(ApiResponseDTO.<List<Evidencia>>builder()
                    .exito(true)
                    .mensaje("Evidencias obtenidas exitosamente")
                    .datos(evidencias)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.<List<Evidencia>>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("EVD_404")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Extrae el ID del usuario desde el token JWT
     * 
     * @param request solicitud HTTP
     * @return ID del usuario
     */
    private Long extraerIdUsuarioDelToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            return jwtTokenProvider.obtenerIdUsuario(token);
        }
        throw new IllegalArgumentException("Token JWT no válido");
    }
}
