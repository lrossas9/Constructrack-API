package com.constructrack.services;

import com.constructrack.dtos.CrearProyectoDTO;
import com.constructrack.entities.Actividad;
import com.constructrack.entities.Proyecto;
import com.constructrack.repositories.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de Proyecto - Gestiona lógica de negocio de proyectos
 * Implementa RF01 - Registro de nuevos proyectos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    /**
     * Crea un nuevo proyecto
     * Implementa RF01 - Optimizado para respuesta menor a 3 segundos (RNF01)
     *
     * @param crearProyectoDTO datos del proyecto a crear
     * @return Proyecto creado
     */
    @SuppressWarnings("null")
    public Proyecto crearProyecto(CrearProyectoDTO crearProyectoDTO) {
        log.info("Creando nuevo proyecto: {}", crearProyectoDTO.getNombre());

        // Validar que las fechas sean válidas
        if (crearProyectoDTO.getFechaFin().isBefore(crearProyectoDTO.getFechaInicio())) {
            log.warn("Fechas inválidas para proyecto: {} - {}", crearProyectoDTO.getFechaInicio(),
                    crearProyectoDTO.getFechaFin());
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        Proyecto nuevoProyecto = new Proyecto();
        nuevoProyecto.setNombre(crearProyectoDTO.getNombre());
        nuevoProyecto.setDescripcion(crearProyectoDTO.getDescripcion());
        nuevoProyecto.setUbicacion(crearProyectoDTO.getUbicacion());
        nuevoProyecto.setFechaInicio(crearProyectoDTO.getFechaInicio());
        nuevoProyecto.setFechaFin(crearProyectoDTO.getFechaFin());
        nuevoProyecto.setEstado(crearProyectoDTO.getEstado() != null ? crearProyectoDTO.getEstado() : "PLANIFICACIÓN");
        nuevoProyecto.setPresupuesto(crearProyectoDTO.getPresupuesto());
        nuevoProyecto.setCliente(crearProyectoDTO.getCliente());
        nuevoProyecto.setContratista(crearProyectoDTO.getContratista());

        Proyecto proyectoGuardado = proyectoRepository.save(nuevoProyecto);
        log.info("Proyecto creado exitosamente: {}", proyectoGuardado.getIdProyecto());

        return proyectoGuardado;
    }

    /**
     * Obtiene todos los proyectos
     * Optimizado para rendimiento (RNF01)
     *
     * @return lista de todos los proyectos
     */
    @Transactional(readOnly = true)
    public List<Proyecto> obtenerTodosProyectos() {
        log.info("Obteniendo todos los proyectos");
        return proyectoRepository.findAll();
    }

    /**
     * Obtiene un proyecto por su ID
     *
     * @param idProyecto ID del proyecto
     * @return Proyecto si existe
     * @throws IllegalArgumentException si no existe
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public Proyecto obtenerProyectoPorId(Long idProyecto) {
        return proyectoRepository.findById(idProyecto)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado"));
    }

    /**
     * Obtiene proyectos por estado
     *
     * @param estado estado de los proyectos a buscar
     * @return lista de proyectos con ese estado
     */
    @Transactional(readOnly = true)
    public List<Proyecto> obtenerProyectosPorEstado(String estado) {
        return proyectoRepository.findByEstado(estado);
    }

    /**
     * Obtiene proyectos por cliente
     *
     * @param cliente nombre del cliente
     * @return lista de proyectos de ese cliente
     */
    @Transactional(readOnly = true)
    public List<Proyecto> obtenerProyectosPorCliente(String cliente) {
        return proyectoRepository.findByCliente(cliente);
    }

    /**
     * Actualiza un proyecto
     *
     * @param idProyecto       ID del proyecto
     * @param crearProyectoDTO nuevos datos
     * @return Proyecto actualizado
     */
    @SuppressWarnings("null")
    public Proyecto actualizarProyecto(Long idProyecto, CrearProyectoDTO crearProyectoDTO) {
        log.info("Actualizando proyecto: {}", idProyecto);

        Proyecto proyecto = obtenerProyectoPorId(idProyecto);

        if (crearProyectoDTO.getNombre() != null) {
            proyecto.setNombre(crearProyectoDTO.getNombre());
        }
        if (crearProyectoDTO.getDescripcion() != null) {
            proyecto.setDescripcion(crearProyectoDTO.getDescripcion());
        }
        if (crearProyectoDTO.getUbicacion() != null) {
            proyecto.setUbicacion(crearProyectoDTO.getUbicacion());
        }
        if (crearProyectoDTO.getFechaInicio() != null) {
            proyecto.setFechaInicio(crearProyectoDTO.getFechaInicio());
        }
        if (crearProyectoDTO.getFechaFin() != null) {
            proyecto.setFechaFin(crearProyectoDTO.getFechaFin());
        }
        if (crearProyectoDTO.getEstado() != null) {
            proyecto.setEstado(crearProyectoDTO.getEstado());
        }
        if (crearProyectoDTO.getPresupuesto() != null) {
            proyecto.setPresupuesto(crearProyectoDTO.getPresupuesto());
        }

        Proyecto proyectoActualizado = proyectoRepository.save(proyecto);
        log.info("Proyecto actualizado: {}", idProyecto);

        return proyectoActualizado;
    }

    /**
     * Elimina un proyecto
     *
     * @param idProyecto ID del proyecto a eliminar
     */
    @SuppressWarnings("null")
    public void eliminarProyecto(Long idProyecto) {
        log.info("Eliminando proyecto: {}", idProyecto);

        obtenerProyectoPorId(idProyecto); // Validar que existe
        proyectoRepository.deleteById(idProyecto);

        log.info("Proyecto eliminado: {}", idProyecto);
    }

    /**
     * Obtiene el avance general de un proyecto sumando el avance de sus actividades
     *
     * @param idProyecto ID del proyecto
     * @return porcentaje de avance promedio
     */
    @Transactional(readOnly = true)
    public Double calcularAvanceProyecto(Long idProyecto) {
        Proyecto proyecto = obtenerProyectoPorId(idProyecto);
        List<Actividad> actividades = proyecto.getActividades();

        if (actividades.isEmpty()) {
            return 0.0;
        }

        double avanceTotal = actividades.stream()
                .mapToInt(a -> a.getPorcentajeAvance() != null ? a.getPorcentajeAvance() : 0)
                .average()
                .orElse(0.0);

        return avanceTotal;
    }
}
