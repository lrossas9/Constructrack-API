package com.constructrack.services;

import com.constructrack.dtos.AvanceProyectoDTO;
import com.constructrack.dtos.RegistrarSeguimientoDTO;
import com.constructrack.entities.Proyecto;
import com.constructrack.entities.Seguimiento;
import com.constructrack.repositories.SeguimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Servicio de Seguimiento - Gestiona lógica de avance de obra
 * Implementa RF02 - Consulta de avance en tiempo real
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;
    private final ProyectoService proyectoService;

    /**
     * Registra un nuevo seguimiento de avance de obra
     * Implementa RF02 con tipo DECIMAL(3,2) para avance_porcentaje
     *
     * @param registrarSeguimientoDTO datos del seguimiento
     * @return Seguimiento creado
     */
    public Seguimiento registrarSeguimiento(RegistrarSeguimientoDTO registrarSeguimientoDTO) {
        log.info("Registrando seguimiento para proyecto: {}", registrarSeguimientoDTO.getIdProyecto());

        Proyecto proyecto = proyectoService.obtenerProyectoPorId(registrarSeguimientoDTO.getIdProyecto());

        // Validar que el porcentaje está en el rango válido (0-100)
        if (registrarSeguimientoDTO.getAvancePorcentaje().compareTo(BigDecimal.ZERO) < 0 ||
                registrarSeguimientoDTO.getAvancePorcentaje().compareTo(new BigDecimal("100")) > 0) {
            log.warn("Porcentaje de avance inválido: {}", registrarSeguimientoDTO.getAvancePorcentaje());
            throw new IllegalArgumentException("El porcentaje de avance debe estar entre 0 y 100");
        }

        Seguimiento nuevoSeguimiento = new Seguimiento();
        nuevoSeguimiento.setAvancePorcentaje(registrarSeguimientoDTO.getAvancePorcentaje());
        nuevoSeguimiento.setFechaSeguimiento(registrarSeguimientoDTO.getFechaSeguimiento());
        nuevoSeguimiento.setObservaciones(registrarSeguimientoDTO.getObservaciones());
        nuevoSeguimiento.setEstado(registrarSeguimientoDTO.getEstado());
        nuevoSeguimiento.setProyecto(proyecto);

        Seguimiento seguimientoGuardado = seguimientoRepository.save(nuevoSeguimiento);
        log.info("Seguimiento registrado exitosamente: {}", seguimientoGuardado.getIdSeguimiento());

        return seguimientoGuardado;
    }

    /**
     * Obtiene el avance actual de un proyecto en tiempo real
     * Implementa RF02 - Retorna el seguimiento más reciente
     *
     * @param idProyecto ID del proyecto
     * @return DTO con información del avance actual
     */
    @Transactional(readOnly = true)
    public AvanceProyectoDTO obtenerAvanceActual(Long idProyecto) {
        log.info("Obteniendo avance actual para proyecto: {}", idProyecto);

        Proyecto proyecto = proyectoService.obtenerProyectoPorId(idProyecto);

        // Obtener el último seguimiento registrado
        Seguimiento ultimoSeguimiento = seguimientoRepository
                .findFirstByProyectoIdProyectoOrderByFechaSeguimientoDesc(idProyecto)
                .orElse(null);

        AvanceProyectoDTO avanceDTO = new AvanceProyectoDTO();
        avanceDTO.setIdProyecto(proyecto.getIdProyecto());
        avanceDTO.setNombreProyecto(proyecto.getNombre());
        avanceDTO.setUbicacion(proyecto.getUbicacion());
        avanceDTO.setFechaInicio(proyecto.getFechaInicio());
        avanceDTO.setFechaFin(proyecto.getFechaFin());
        avanceDTO.setEstado(proyecto.getEstado());

        if (ultimoSeguimiento != null) {
            avanceDTO.setPorcentajeAvance(ultimoSeguimiento.getAvancePorcentaje());
            avanceDTO.setUltimaActualizacion(ultimoSeguimiento.getFechaSeguimiento());
            avanceDTO.setObservaciones(ultimoSeguimiento.getObservaciones());
        } else {
            avanceDTO.setPorcentajeAvance(BigDecimal.ZERO);
            avanceDTO.setUltimaActualizacion(proyecto.getFechaCreacion().toLocalDate());
            avanceDTO.setObservaciones("Sin seguimiento registrado aún");
        }

        return avanceDTO;
    }

    /**
     * Obtiene todos los seguimientos de un proyecto
     *
     * @param idProyecto ID del proyecto
     * @return lista de seguimientos del proyecto ordenados por fecha descendente
     */
    @Transactional(readOnly = true)
    public List<Seguimiento> obtenerSeguimientosPorProyecto(Long idProyecto) {
        proyectoService.obtenerProyectoPorId(idProyecto); // Validar que el proyecto existe
        return seguimientoRepository.findByProyectoIdProyectoOrderByFechaSeguimientoDesc(idProyecto);
    }

    /**
     * Obtiene un seguimiento por su ID
     *
     * @param idSeguimiento ID del seguimiento
     * @return Seguimiento si existe
     * @throws IllegalArgumentException si no existe
     */
    @Transactional(readOnly = true)
    public Seguimiento obtenerSeguimientoPorId(Long idSeguimiento) {
        return seguimientoRepository.findById(idSeguimiento)
                .orElseThrow(() -> new IllegalArgumentException("Seguimiento no encontrado"));
    }

    /**
     * Actualiza un seguimiento
     *
     * @param idSeguimiento ID del seguimiento
     * @param registrarSeguimientoDTO nuevos datos
     * @return Seguimiento actualizado
     */
    public Seguimiento actualizarSeguimiento(Long idSeguimiento, RegistrarSeguimientoDTO registrarSeguimientoDTO) {
        log.info("Actualizando seguimiento: {}", idSeguimiento);

        Seguimiento seguimiento = obtenerSeguimientoPorId(idSeguimiento);

        if (registrarSeguimientoDTO.getAvancePorcentaje() != null) {
            seguimiento.setAvancePorcentaje(registrarSeguimientoDTO.getAvancePorcentaje());
        }
        if (registrarSeguimientoDTO.getFechaSeguimiento() != null) {
            seguimiento.setFechaSeguimiento(registrarSeguimientoDTO.getFechaSeguimiento());
        }
        if (registrarSeguimientoDTO.getObservaciones() != null) {
            seguimiento.setObservaciones(registrarSeguimientoDTO.getObservaciones());
        }
        if (registrarSeguimientoDTO.getEstado() != null) {
            seguimiento.setEstado(registrarSeguimientoDTO.getEstado());
        }

        Seguimiento seguimientoActualizado = seguimientoRepository.save(seguimiento);
        log.info("Seguimiento actualizado: {}", idSeguimiento);

        return seguimientoActualizado;
    }

    /**
     * Elimina un seguimiento
     *
     * @param idSeguimiento ID del seguimiento a eliminar
     */
    public void eliminarSeguimiento(Long idSeguimiento) {
        log.info("Eliminando seguimiento: {}", idSeguimiento);

        obtenerSeguimientoPorId(idSeguimiento); // Validar que existe
        seguimientoRepository.deleteById(idSeguimiento);

        log.info("Seguimiento eliminado: {}", idSeguimiento);
    }
}
