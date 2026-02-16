package com.constructrack.repositories;

import com.constructrack.entities.ReporteDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteDiarioRepository extends JpaRepository<ReporteDiario, Long> {
    List<ReporteDiario> findByProyectoIdProyectoOrderByFechaDesc(Long idProyecto);

    List<ReporteDiario> findByUsuarioIdUsuarioOrderByFechaDesc(Long idUsuario);

    List<ReporteDiario> findByProyectoIdProyectoAndFechaBetween(Long idProyecto, LocalDate fechaInicio,
            LocalDate fechaFin);
    // ...otros métodos personalizados si se requieren...
}
