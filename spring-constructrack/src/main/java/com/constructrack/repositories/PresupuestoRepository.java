package com.constructrack.repositories;

import com.constructrack.entities.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
    List<Presupuesto> findByProyectoIdProyecto(Long idProyecto);

    List<Presupuesto> findByActivoTrue();
}
