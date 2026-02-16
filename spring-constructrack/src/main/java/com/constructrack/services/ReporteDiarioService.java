package com.constructrack.services;

import com.constructrack.dtos.RegistrarReporteDiarioDTO;
import com.constructrack.entities.Actividad;
import com.constructrack.entities.Proyecto;
import com.constructrack.entities.ReporteDiario;
import com.constructrack.entities.Usuario;
import com.constructrack.repositories.ReporteDiarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio de ReporteDiario - Gestiona reportes diarios de obra
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReporteDiarioService {

    private final ReporteDiarioRepository reporteDiarioRepository;
    private final ProyectoService proyectoService;
    private final ActividadService actividadService;
    private final UsuarioService usuarioService;

    /**
     * Registra un nuevo reporte diario de obra
     *
     * @param registrarReporteDiarioDTO datos del reporte
     * @param idUsuario ID del usuario que registra el reporte
     * @return ReporteDiario creado
     */
    @SuppressWarnings("null")
    public ReporteDiario registrarReporteDiario(RegistrarReporteDiarioDTO registrarReporteDiarioDTO, Long idUsuario) {
        log.info("Registrando reporte diario para proyecto: {}", registrarReporteDiarioDTO.getIdProyecto());

        Proyecto proyecto = proyectoService.obtenerProyectoPorId(registrarReporteDiarioDTO.getIdProyecto());
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

        ReporteDiario nuevoReporte = new ReporteDiario();
        nuevoReporte.setFecha(registrarReporteDiarioDTO.getFecha());
        nuevoReporte.setClima(registrarReporteDiarioDTO.getClima());
        nuevoReporte.setObservaciones(registrarReporteDiarioDTO.getObservaciones());
        nuevoReporte.setCantidadTrabajadores(registrarReporteDiarioDTO.getCantidadTrabajadores());
        nuevoReporte.setHorasTrabajadas(registrarReporteDiarioDTO.getHorasTrabajadas());
        nuevoReporte.setNovedades(registrarReporteDiarioDTO.getNovedades());
        nuevoReporte.setIncidentes(registrarReporteDiarioDTO.getIncidentes());
        nuevoReporte.setMaterialesUtilizados(registrarReporteDiarioDTO.getMaterialesUtilizados());
        nuevoReporte.setProyecto(proyecto);
        nuevoReporte.setUsuario(usuario);

        // Asociar actividad si se proporciona
        if (registrarReporteDiarioDTO.getIdActividad() != null) {
            Actividad actividad = actividadService.obtenerActividadPorId(registrarReporteDiarioDTO.getIdActividad());
            nuevoReporte.setActividad(actividad);
        }

        ReporteDiario reporteGuardado = reporteDiarioRepository.save(nuevoReporte);
        log.info("Reporte diario registrado exitosamente: {}", reporteGuardado.getIdReporteDiario());

        return reporteGuardado;
    }

    /**
     * Obtiene todos los reportes de un proyecto
     *
     * @param idProyecto ID del proyecto
     * @return lista de reportes del proyecto
     */
    @Transactional(readOnly = true)
    public List<ReporteDiario> obtenerReportesPorProyecto(Long idProyecto) {
        proyectoService.obtenerProyectoPorId(idProyecto); // Validar que el proyecto existe
        return reporteDiarioRepository.findByProyectoIdProyectoOrderByFechaDesc(idProyecto);
    }

    /**
     * Obtiene reportes de un usuario
     *
     * @param idUsuario ID del usuario
     * @return lista de reportes del usuario
     */
    @Transactional(readOnly = true)
    public List<ReporteDiario> obtenerReportesPorUsuario(Long idUsuario) {
        usuarioService.obtenerUsuarioPorId(idUsuario); // Validar que el usuario existe
        return reporteDiarioRepository.findByUsuarioIdUsuarioOrderByFechaDesc(idUsuario);
    }

    /**
     * Obtiene un reporte por su ID
     *
     * @param idReporteDiario ID del reporte
     * @return ReporteDiario si existe
     * @throws IllegalArgumentException si no existe
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public ReporteDiario obtenerReportePorId(Long idReporteDiario) {
        return reporteDiarioRepository.findById(idReporteDiario)
                .orElseThrow(() -> new IllegalArgumentException("Reporte diario no encontrado"));
    }

    /**
     * Obtiene reportes en un rango de fechas para un proyecto
     *
     * @param idProyecto ID del proyecto
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de reportes en el rango de fechas
     */
    @Transactional(readOnly = true)
    public List<ReporteDiario> obtenerReportesPorRangoFechas(Long idProyecto, LocalDate fechaInicio, LocalDate fechaFin) {
        proyectoService.obtenerProyectoPorId(idProyecto); // Validar que el proyecto existe
        return reporteDiarioRepository.findByProyectoIdProyectoAndFechaBetween(idProyecto, fechaInicio, fechaFin);
    }

    /**
     * Actualiza un reporte diario
     *
     * @param idReporteDiario ID del reporte
     * @param registrarReporteDiarioDTO nuevos datos
     * @return ReporteDiario actualizado
     */
    @SuppressWarnings("null")
    public ReporteDiario actualizarReporteDiario(Long idReporteDiario, RegistrarReporteDiarioDTO registrarReporteDiarioDTO) {
        log.info("Actualizando reporte diario: {}", idReporteDiario);

        ReporteDiario reporte = obtenerReportePorId(idReporteDiario);

        if (registrarReporteDiarioDTO.getFecha() != null) {
            reporte.setFecha(registrarReporteDiarioDTO.getFecha());
        }
        if (registrarReporteDiarioDTO.getClima() != null) {
            reporte.setClima(registrarReporteDiarioDTO.getClima());
        }
        if (registrarReporteDiarioDTO.getObservaciones() != null) {
            reporte.setObservaciones(registrarReporteDiarioDTO.getObservaciones());
        }
        if (registrarReporteDiarioDTO.getCantidadTrabajadores() != null) {
            reporte.setCantidadTrabajadores(registrarReporteDiarioDTO.getCantidadTrabajadores());
        }
        if (registrarReporteDiarioDTO.getHorasTrabajadas() != null) {
            reporte.setHorasTrabajadas(registrarReporteDiarioDTO.getHorasTrabajadas());
        }

        ReporteDiario reporteActualizado = reporteDiarioRepository.save(reporte);
        log.info("Reporte diario actualizado: {}", idReporteDiario);

        return reporteActualizado;
    }

    /**
     * Elimina un reporte diario
     *
     * @param idReporteDiario ID del reporte a eliminar
     */
    @SuppressWarnings("null")
    public void eliminarReporteDiario(Long idReporteDiario) {
        log.info("Eliminando reporte diario: {}", idReporteDiario);

        obtenerReportePorId(idReporteDiario); // Validar que existe
        reporteDiarioRepository.deleteById(idReporteDiario);

        log.info("Reporte diario eliminado: {}", idReporteDiario);
    }
}
