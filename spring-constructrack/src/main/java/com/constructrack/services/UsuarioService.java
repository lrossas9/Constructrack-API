package com.constructrack.services;

import com.constructrack.dtos.ActualizarUsuarioDTO;
import com.constructrack.dtos.AuthResponseDTO;
import com.constructrack.dtos.LoginDTO;
import com.constructrack.dtos.RegistroUsuarioDTO;
import com.constructrack.entities.Usuario;
import com.constructrack.repositories.UsuarioRepository;
import com.constructrack.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de Usuario - Gestiona lógica de negocio relacionada con usuarios
 * Implementa RF de autenticación y registro
 * Cumple con RNF de seguridad mediante encriptación de contraseñas
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Registra un nuevo usuario en el sistema
     * Aplica validaciones de seguridad y encripta la contraseña
     *
     * @param registroDTO datos del usuario a registrar
     * @return Usuario creado
     * @throws IllegalArgumentException si el usuario ya existe
     */
    public Usuario registrarUsuario(RegistroUsuarioDTO registroDTO) {
        log.info("Registrando nuevo usuario: {}", registroDTO.getNombreUsuario());

        // Validar que el usuario no exista
        if (usuarioRepository.existsByNombreUsuario(registroDTO.getNombreUsuario())) {
            log.warn("Intento de registrar usuario existente: {}", registroDTO.getNombreUsuario());
            throw new IllegalArgumentException("El usuario ya está registrado");
        }

        if (usuarioRepository.existsByCorreo(registroDTO.getCorreo())) {
            log.warn("Intento de registrar correo existente: {}", registroDTO.getCorreo());
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(registroDTO.getNombreUsuario());
        nuevoUsuario.setCorreo(registroDTO.getCorreo());
        // Encriptar contraseña con BCrypt
        nuevoUsuario.setContrasena(passwordEncoder.encode(registroDTO.getContrasena()));
        nuevoUsuario.setRol(Usuario.RolEnum.valueOf(registroDTO.getRol()));
        nuevoUsuario.setNombre(registroDTO.getNombre());
        nuevoUsuario.setApellido(registroDTO.getApellido());
        nuevoUsuario.setTelefono(registroDTO.getTelefono());
        nuevoUsuario.setActivo(true);

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        log.info("Usuario registrado exitosamente: {}", usuarioGuardado.getIdUsuario());

        return usuarioGuardado;
    }

    /**
     * Autentica un usuario y retorna un token JWT
     *
     * @param loginDTO credenciales del usuario
     * @return datos de autenticación incluyendo token JWT
     * @throws IllegalArgumentException si las credenciales son inválidas
     */
    public AuthResponseDTO autenticar(LoginDTO loginDTO) {
        log.info("Intento de autenticación para usuario: {}", loginDTO.getNombreUsuario());

        Usuario usuario = usuarioRepository.findByNombreUsuario(loginDTO.getNombreUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña inválidos"));

        // Validar contraseña
        if (!passwordEncoder.matches(loginDTO.getContrasena(), usuario.getContrasena())) {
            log.warn("Contraseña inválida para usuario: {}", loginDTO.getNombreUsuario());
            throw new IllegalArgumentException("Usuario o contraseña inválidos");
        }

        if (!usuario.getActivo()) {
            log.warn("Intento de autenticar usuario inactivo: {}", loginDTO.getNombreUsuario());
            throw new IllegalArgumentException("El usuario está inactivo");
        }

        // Generar token JWT
        String token = jwtTokenProvider.generarToken(usuario.getIdUsuario(), usuario.getNombreUsuario(), usuario.getRol().toString());

        log.info("Usuario autenticado exitosamente: {}", usuario.getIdUsuario());

        return AuthResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreUsuario(usuario.getNombreUsuario())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol().toString())
                .token(token)
                .mensaje("Autenticación exitosa")
                .build();
    }

    /**
     * Obtiene todos los usuarios
     *
     * @return lista de usuarios
     */
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Obtiene un usuario por su ID
     *
     * @param idUsuario ID del usuario
     * @return Usuario si existe
     * @throws IllegalArgumentException si no existe
     */
    @Transactional(readOnly = true)
    public Usuario obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    /**
     * Actualiza el perfil de un usuario
     *
     * @param idUsuario ID del usuario a actualizar
     * @param actualizarDTO nuevos datos
     * @return Usuario actualizado
     */
    public Usuario actualizarUsuario(Long idUsuario, ActualizarUsuarioDTO actualizarDTO) {
        log.info("Actualizando usuario: {}", idUsuario);

        Usuario usuario = obtenerUsuarioPorId(idUsuario);

        if (actualizarDTO.getNombre() != null) {
            usuario.setNombre(actualizarDTO.getNombre());
        }
        if (actualizarDTO.getApellido() != null) {
            usuario.setApellido(actualizarDTO.getApellido());
        }
        if (actualizarDTO.getCorreo() != null) {
            usuario.setCorreo(actualizarDTO.getCorreo());
        }
        if (actualizarDTO.getTelefono() != null) {
            usuario.setTelefono(actualizarDTO.getTelefono());
        }
        if (actualizarDTO.getRol() != null) {
            usuario.setRol(Usuario.RolEnum.valueOf(actualizarDTO.getRol()));
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        log.info("Usuario actualizado: {}", idUsuario);

        return usuarioActualizado;
    }

    /**
     * Obtiene un usuario por nombre de usuario
     *
     * @param nombreUsuario nombre del usuario
     * @return Optional con el usuario si existe
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    /**
     * Desactiva un usuario
     *
     * @param idUsuario ID del usuario a desactivar
     */
    public void desactivarUsuario(Long idUsuario) {
        log.info("Desactivando usuario: {}", idUsuario);

        Usuario usuario = obtenerUsuarioPorId(idUsuario);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);

        log.info("Usuario desactivado: {}", idUsuario);
    }
}
