package com.constructrack.controllers;

import com.constructrack.dtos.ApiResponseDTO;
import com.constructrack.dtos.AuthResponseDTO;
import com.constructrack.dtos.LoginDTO;
import com.constructrack.dtos.RegistroUsuarioDTO;
import com.constructrack.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Controlador de Autenticación - Gestiona login y registro de usuarios
 * 
 * Endpoints públicos (sin autenticación):
 * - POST /api/auth/login
 * - POST /api/usuarios/registro
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticación", description = "Endpoints para autenticación y registro de usuarios")
public class AuthController {

    private final UsuarioService usuarioService;

    /**
     * Autentica un usuario y devuelve un token JWT
     * 
     * @param loginDTO credenciales del usuario
     * @return token JWT y datos del usuario
     */
    @PostMapping("/login")
    @Operation(
            summary = "Autenticar usuario",
            description = "Valida las credenciales del usuario y retorna un token JWT para futuras solicitudes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Credenciales inválidas"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        log.info("Intento de autenticación para usuario: {}", loginDTO.getNombreUsuario());

        try {
            AuthResponseDTO authResponse = usuarioService.autenticar(loginDTO);

            return ResponseEntity.ok(ApiResponseDTO.<AuthResponseDTO>builder()
                    .exito(true)
                    .mensaje("Autenticación exitosa")
                    .datos(authResponse)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (IllegalArgumentException e) {
            log.warn("Falló autenticación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.<AuthResponseDTO>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("AUTH_001")
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error durante autenticación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.<AuthResponseDTO>builder()
                            .exito(false)
                            .mensaje("Error interno del servidor")
                            .codigoError("AUTH_500")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }

    /**
     * Registra un nuevo usuario en el sistema
     * Implementa seguridad de contraseña con BCrypt
     * 
     * @param registroDTO datos del usuario a registrar
     * @return usuario registrado (sin contraseña)
     */
    @PostMapping("/registrarse")
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea un nuevo usuario en el sistema con validaciones de seguridad",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario ya existe"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    public ResponseEntity<ApiResponseDTO<?>> registrarse(@Valid @RequestBody RegistroUsuarioDTO registroDTO) {
        log.info("Nuevo registro de usuario: {}", registroDTO.getNombreUsuario());

        try {
            usuarioService.registrarUsuario(registroDTO);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.builder()
                            .exito(true)
                            .mensaje("Usuario registrado exitosamente. Ahora puede iniciar sesión")
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (IllegalArgumentException e) {
            log.warn("Falló registro: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .codigoError("REG_001")
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (Exception e) {
            log.error("Error durante registro", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .exito(false)
                            .mensaje("Error interno del servidor")
                            .codigoError("REG_500")
                            .timestamp(LocalDateTime.now())
                            .build());
        }
    }
}
