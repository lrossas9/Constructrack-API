package com.constructrack.repository;

import com.constructrack.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    List<Actividad> findByProyectoIdProyecto(Long idProyecto);
}
