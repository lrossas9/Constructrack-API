package com.constructrack.controllers;

import com.constructrack.entities.Usuario;
import com.constructrack.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR_SISTEMA')")
public class UsuarioAdminController {
    private final UsuarioRepository usuarioRepository;

    @SuppressWarnings("null")
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @SuppressWarnings("null")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @SuppressWarnings("null")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setIdUsuario(id);
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @SuppressWarnings("null")
    @PutMapping("/{id}/bloquear")
    public ResponseEntity<Usuario> bloquearUsuario(@PathVariable Long id, @RequestParam boolean activo) {
        return usuarioRepository.findById(id)
                .map(u -> {
                    u.setActivo(activo);
                    return ResponseEntity.ok(usuarioRepository.save(u));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @SuppressWarnings("null")
    @PutMapping("/{id}/rol")
    public ResponseEntity<Usuario> asignarRol(@PathVariable Long id, @RequestParam String rol) {
        return usuarioRepository.findById(id)
                .map(u -> {
                    u.setRol(Usuario.RolEnum.valueOf(rol));
                    return ResponseEntity.ok(usuarioRepository.save(u));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/recuperar")
    public ResponseEntity<String> recuperarContrasena(@PathVariable Long id) {
        // Simulación de envío de recuperación
        return ResponseEntity.ok("Correo de recuperación enviado al usuario " + id);
    }

    @SuppressWarnings("null")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(u -> {
                    u.setActivo(false);
                    usuarioRepository.save(u);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
