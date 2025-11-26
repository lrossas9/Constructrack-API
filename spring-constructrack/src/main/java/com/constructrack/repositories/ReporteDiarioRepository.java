package com.constructrack.repositories;

import com.constructrack.entities.ReporteDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para la entidad ReporteDiario
 */
@Repository
public interface ReporteDiarioRepository extends JpaRepository<ReporteDiario, Long> {

    /**
     * Encuentra reportes diarios por proyecto
     * @param idProyecto el ID del proyecto
     * @return lista de reportes del proyecto
     */
    List<ReporteDiario> findByProyectoIdProyectoOrderByFechaDesc(Long idProyecto);

    /**
     * Encuentra reportes diarios por usuario
     * @param idUsuario el ID del usuario
     * @return lista de reportes del usuario
     */
    List<ReporteDiario> findByUsuarioIdUsuarioOrderByFechaDesc(Long idUsuario);

    /**
     * Encuentra reportes diarios en un rango de fechas
     * @param idProyecto el ID del proyecto
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de reportes en el rango de fechas
     */
    List<ReporteDiario> findByProyectoIdProyectoAndFechaBetween(Long idProyecto, LocalDate fechaInicio, LocalDate fechaFin);
}
