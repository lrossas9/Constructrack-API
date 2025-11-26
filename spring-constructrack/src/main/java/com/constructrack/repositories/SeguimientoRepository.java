package com.constructrack.repositories;

import com.constructrack.entities.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Seguimiento
 * Implementa RF02 - Consulta de avance en tiempo real
 */
@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {

    /**
     * Encuentra seguimientos por proyecto
     * @param idProyecto el ID del proyecto
     * @return lista de seguimientos del proyecto ordenados por fecha descendente
     */
    List<Seguimiento> findByProyectoIdProyectoOrderByFechaSeguimientoDesc(Long idProyecto);

    /**
     * Obtiene el último seguimiento de un proyecto (más reciente)
     * @param idProyecto el ID del proyecto
     * @return Optional con el último seguimiento
     */
    @Query("SELECT s FROM Seguimiento s WHERE s.proyecto.idProyecto = :idProyecto ORDER BY s.fechaSeguimiento DESC LIMIT 1")
    Optional<Seguimiento> obtenerUltimoSeguimiento(@Param("idProyecto") Long idProyecto);

    /**
     * Obtiene el avance actual de un proyecto
     * @param idProyecto el ID del proyecto
     * @return Optional con el seguimiento más reciente que contiene el avance actual
     */
    Optional<Seguimiento> findFirstByProyectoIdProyectoOrderByFechaSeguimientoDesc(Long idProyecto);
}
