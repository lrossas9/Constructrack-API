package com.constructrack.service;

import com.constructrack.dto.UsuarioDto;
import com.constructrack.model.Usuario;
import com.constructrack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Validación de contraseña: mínimo 8 caracteres, al menos un número y un símbolo
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$");

    public Usuario crearUsuario(UsuarioDto dto) {
        if (!PASSWORD_PATTERN.matcher(dto.getContraseña()).matches()) {
            throw new IllegalArgumentException("La contraseña debe tener mínimo 8 caracteres, incluir número y símbolo");
        }

        if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("Correo ya registrado");
        }

        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setCorreo(dto.getCorreo());
        u.setContraseña(passwordEncoder.encode(dto.getContraseña()));
        u.setRol(dto.getRol());

        return usuarioRepository.save(u);
    }

    public Usuario findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
