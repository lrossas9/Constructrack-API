package com.constructrack.repositories;

import com.constructrack.entities.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Actividad
 */
@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    /**
     * Encuentra actividades por proyecto
     * @param idProyecto el ID del proyecto
     * @return lista de actividades del proyecto
     */
    List<Actividad> findByProyectoIdProyecto(Long idProyecto);

    /**
     * Encuentra actividades por estado
     * @param estado el estado a buscar
     * @return lista de actividades con ese estado
     */
    List<Actividad> findByEstado(String estado);
}
