package com.constructrack.services;

import com.constructrack.dtos.CrearActividadDTO;
import com.constructrack.entities.Actividad;
import com.constructrack.entities.Proyecto;
import com.constructrack.repositories.ActividadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de Actividad - Gestiona lógica de negocio de actividades
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ActividadService {

    private final ActividadRepository actividadRepository;
    private final ProyectoService proyectoService;

    /**
     * Crea una nueva actividad para un proyecto
     *
     * @param idProyecto ID del proyecto
     * @param crearActividadDTO datos de la actividad
     * @return Actividad creada
     */
    public Actividad crearActividad(Long idProyecto, CrearActividadDTO crearActividadDTO) {
        log.info("Creando nueva actividad para proyecto: {}", idProyecto);

        Proyecto proyecto = proyectoService.obtenerProyectoPorId(idProyecto);

        if (crearActividadDTO.getFechaFin().isBefore(crearActividadDTO.getFechaInicio())) {
            log.warn("Fechas inválidas para actividad: {} - {}", crearActividadDTO.getFechaInicio(), crearActividadDTO.getFechaFin());
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        Actividad nuevaActividad = new Actividad();
        nuevaActividad.setNombre(crearActividadDTO.getNombre());
        nuevaActividad.setDescripcion(crearActividadDTO.getDescripcion());
        nuevaActividad.setFechaInicio(crearActividadDTO.getFechaInicio());
        nuevaActividad.setFechaFin(crearActividadDTO.getFechaFin());
        nuevaActividad.setEstado(crearActividadDTO.getEstado() != null ? crearActividadDTO.getEstado() : "PENDIENTE");
        nuevaActividad.setPorcentajeAvance(crearActividadDTO.getPorcentajeAvance() != null ? crearActividadDTO.getPorcentajeAvance() : 0);
        nuevaActividad.setResponsable(crearActividadDTO.getResponsable());
        nuevaActividad.setPresupuestoActividad(crearActividadDTO.getPresupuestoActividad());
        nuevaActividad.setProyecto(proyecto);

        Actividad actividadGuardada = actividadRepository.save(nuevaActividad);
        log.info("Actividad creada exitosamente: {}", actividadGuardada.getIdActividad());

        return actividadGuardada;
    }

    /**
     * Obtiene todas las actividades de un proyecto
     *
     * @param idProyecto ID del proyecto
     * @return lista de actividades del proyecto
     */
    @Transactional(readOnly = true)
    public List<Actividad> obtenerActividadesPorProyecto(Long idProyecto) {
        proyectoService.obtenerProyectoPorId(idProyecto); // Validar que el proyecto existe
        return actividadRepository.findByProyectoIdProyecto(idProyecto);
    }

    /**
     * Obtiene una actividad por su ID
     *
     * @param idActividad ID de la actividad
     * @return Actividad si existe
     * @throws IllegalArgumentException si no existe
     */
    @Transactional(readOnly = true)
    public Actividad obtenerActividadPorId(Long idActividad) {
        return actividadRepository.findById(idActividad)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada"));
    }

    /**
     * Actualiza una actividad
     *
     * @param idActividad ID de la actividad
     * @param crearActividadDTO nuevos datos
     * @return Actividad actualizada
     */
    public Actividad actualizarActividad(Long idActividad, CrearActividadDTO crearActividadDTO) {
        log.info("Actualizando actividad: {}", idActividad);

        Actividad actividad = obtenerActividadPorId(idActividad);

        if (crearActividadDTO.getNombre() != null) {
            actividad.setNombre(crearActividadDTO.getNombre());
        }
        if (crearActividadDTO.getDescripcion() != null) {
            actividad.setDescripcion(crearActividadDTO.getDescripcion());
        }
        if (crearActividadDTO.getFechaInicio() != null) {
            actividad.setFechaInicio(crearActividadDTO.getFechaInicio());
        }
        if (crearActividadDTO.getFechaFin() != null) {
            actividad.setFechaFin(crearActividadDTO.getFechaFin());
        }
        if (crearActividadDTO.getEstado() != null) {
            actividad.setEstado(crearActividadDTO.getEstado());
        }
        if (crearActividadDTO.getPorcentajeAvance() != null) {
            actividad.setPorcentajeAvance(crearActividadDTO.getPorcentajeAvance());
        }
        if (crearActividadDTO.getResponsable() != null) {
            actividad.setResponsable(crearActividadDTO.getResponsable());
        }

        Actividad actividadActualizada = actividadRepository.save(actividad);
        log.info("Actividad actualizada: {}", idActividad);

        return actividadActualizada;
    }

    /**
     * Elimina una actividad
     *
     * @param idActividad ID de la actividad a eliminar
     */
    public void eliminarActividad(Long idActividad) {
        log.info("Eliminando actividad: {}", idActividad);

        obtenerActividadPorId(idActividad); // Validar que existe
        actividadRepository.deleteById(idActividad);

        log.info("Actividad eliminada: {}", idActividad);
    }

    /**
     * Obtiene actividades por estado
     *
     * @param estado estado a buscar
     * @return lista de actividades con ese estado
     */
    @Transactional(readOnly = true)
    public List<Actividad> obtenerActividadesPorEstado(String estado) {
        return actividadRepository.findByEstado(estado);
    }
}
