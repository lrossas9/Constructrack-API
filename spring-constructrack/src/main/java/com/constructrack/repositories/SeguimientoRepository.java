package com.constructrack.repositories;

import com.constructrack.entities.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {
    Optional<Seguimiento> findFirstByProyectoIdProyectoOrderByFechaSeguimientoDesc(Long idProyecto);

    List<Seguimiento> findByProyectoIdProyectoOrderByFechaSeguimientoDesc(Long idProyecto);
    // ...otros métodos personalizados si se requieren...
}
