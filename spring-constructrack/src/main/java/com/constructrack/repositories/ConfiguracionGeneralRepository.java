package com.constructrack.repositories;

import com.constructrack.entities.ConfiguracionGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracionGeneralRepository extends JpaRepository<ConfiguracionGeneral, Long> {
    Optional<ConfiguracionGeneral> findByParametro(String parametro);
}
