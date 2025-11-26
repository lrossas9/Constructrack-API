package com.constructrack.repositories;

import com.constructrack.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Usuario
 * Proporciona operaciones CRUD y consultas personalizadas
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Encuentra un usuario por nombre de usuario
     * @param nombreUsuario el nombre de usuario a buscar
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    /**
     * Encuentra un usuario por correo
     * @param correo el correo a buscar
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByCorreo(String correo);

    /**
     * Verifica si existe un usuario con un nombre de usuario específico
     * @param nombreUsuario el nombre de usuario a verificar
     * @return true si existe, false si no
     */
    boolean existsByNombreUsuario(String nombreUsuario);

    /**
     * Verifica si existe un usuario con un correo específico
     * @param correo el correo a verificar
     * @return true si existe, false si no
     */
    boolean existsByCorreo(String correo);
}
