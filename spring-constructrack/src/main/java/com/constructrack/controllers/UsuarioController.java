
package com.constructrack.controllers;

import com.constructrack.dtos.ActualizarUsuarioDTO;
import com.constructrack.dtos.ApiResponseDTO;
import com.constructrack.entities.Usuario;
import com.constructrack.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
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
 * Controlador de Usuario - Gestiona operaciones de perfil de usuario
 */
@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuario", description = "Operaciones de usuario")
@Slf4j
@RequiredArgsConstructor
public class UsuarioController {

        private final UsuarioService usuarioService;

        /**
         * Obtiene un usuario por su ID
         * 
         * @param id ID del usuario
         * @return datos del usuario
         */
        @GetMapping("/{id}")
        @Operation(summary = "Obtener usuario por ID", description = "Retorna los datos de un usuario específico", responses = {
                        @ApiResponse(responseCode = "200", description = "Usuario obtenido exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Usuario no encontrado"),
                        @ApiResponse(responseCode = "401", description = "No autenticado")
        })
        public ResponseEntity<ApiResponseDTO<Usuario>> obtenerPorId(@PathVariable Long id) {
                try {
                        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
                        return ResponseEntity.ok(ApiResponseDTO.<Usuario>builder()
                                        .exito(true)
                                        .mensaje("Usuario obtenido exitosamente")
                                        .datos(usuario)
                                        .timestamp(LocalDateTime.now())
                                        .build());
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ApiResponseDTO.<Usuario>builder()
                                                        .exito(false)
                                                        .mensaje(e.getMessage())
                                                        .codigoError("USR_404")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                }
        }

        /**
         * Actualiza el perfil de un usuario
         * Implementa RF de actualización de datos de perfil
         * 
         * @param id            ID del usuario a actualizar
         * @param actualizarDTO nuevos datos del usuario
         * @return usuario actualizado
         */
        @PutMapping("/{id}")
        @Operation(summary = "Actualizar perfil de usuario", description = "Actualiza los datos del perfil de un usuario", responses = {
                        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
                        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                        @ApiResponse(responseCode = "401", description = "No autenticado"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
        public ResponseEntity<ApiResponseDTO<Usuario>> actualizar(
                        @PathVariable Long id,
                        @Valid @RequestBody ActualizarUsuarioDTO actualizarDTO) {
                log.info("Actualizando usuario: {}", id);
                try {
                        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, actualizarDTO);
                        return ResponseEntity.ok(ApiResponseDTO.<Usuario>builder()
                                        .exito(true)
                                        .mensaje("Usuario actualizado exitosamente")
                                        .datos(usuarioActualizado)
                                        .timestamp(LocalDateTime.now())
                                        .build());
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ApiResponseDTO.<Usuario>builder()
                                                        .exito(false)
                                                        .mensaje(e.getMessage())
                                                        .codigoError("USR_404")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                } catch (Exception e) {
                        log.error("Error al actualizar usuario", e);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body(ApiResponseDTO.<Usuario>builder()
                                                        .exito(false)
                                                        .mensaje("Error interno del servidor")
                                                        .codigoError("USR_500")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                }
        }

        /**
         * Desactiva un usuario
         * 
         * @param id ID del usuario a desactivar
         */
        @DeleteMapping("/{id}")
        @Operation(summary = "Desactivar usuario", description = "Desactiva un usuario del sistema", responses = {
                        @ApiResponse(responseCode = "200", description = "Usuario desactivado exitosamente"),
                        @ApiResponse(responseCode = "401", description = "No autenticado"),
                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        })
        public ResponseEntity<ApiResponseDTO<?>> desactivar(@PathVariable Long id) {
                log.info("Desactivando usuario: {}", id);
                try {
                        usuarioService.desactivarUsuario(id);
                        return ResponseEntity.ok(ApiResponseDTO.builder()
                                        .exito(true)
                                        .mensaje("Usuario desactivado exitosamente")
                                        .timestamp(LocalDateTime.now())
                                        .build());
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                        .body(ApiResponseDTO.builder()
                                                        .exito(false)
                                                        .mensaje(e.getMessage())
                                                        .codigoError("USR_404")
                                                        .timestamp(LocalDateTime.now())
                                                        .build());
                }
        }
}
