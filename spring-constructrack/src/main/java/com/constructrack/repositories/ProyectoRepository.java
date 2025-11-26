package com.constructrack.repositories;

import com.constructrack.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Proyecto
 */
@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    /**
     * Encuentra proyectos por estado
     * @param estado el estado a buscar
     * @return lista de proyectos con ese estado
     */
    List<Proyecto> findByEstado(String estado);

    /**
     * Encuentra proyectos por cliente
     * @param cliente el cliente a buscar
     * @return lista de proyectos de ese cliente
     */
    List<Proyecto> findByCliente(String cliente);
}
