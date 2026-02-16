package com.constructrack.repositories;

import com.constructrack.entities.AvanceDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvanceDiarioRepository extends JpaRepository<AvanceDiario, Long> {
    List<AvanceDiario> findByProyectoIdProyecto(Long idProyecto);

    List<AvanceDiario> findByActividadIdActividad(Long idActividad);

    List<AvanceDiario> findByFecha(LocalDate fecha);

    List<AvanceDiario> findByActivoTrue();
}
