package com.constructrack.repositories;

import com.constructrack.entities.BitacoraObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BitacoraObraRepository extends JpaRepository<BitacoraObra, Long> {
    List<BitacoraObra> findByProyectoIdProyecto(Long idProyecto);

    List<BitacoraObra> findByFecha(LocalDate fecha);

    List<BitacoraObra> findByActivoTrue();
}
