package com.constructrack.controller;

import com.constructrack.dto.LoginRequest;
import com.constructrack.dto.UsuarioDto;
import com.constructrack.model.Usuario;
import com.constructrack.service.UsuarioService;
import com.constructrack.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDto dto) {
        Usuario u = usuarioService.crearUsuario(dto);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getCorreo(), login.getContraseña()));
            Usuario u = usuarioService.findByCorreo(login.getCorreo());
            String token = jwtUtil.generateToken(u.getCorreo());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
