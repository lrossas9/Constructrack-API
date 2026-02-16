package com.constructrack.controllers;

import com.constructrack.entities.ConfiguracionGeneral;
import com.constructrack.services.ConfiguracionGeneralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuracion")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR_SISTEMA')")
public class ConfiguracionGeneralController {
    private final ConfiguracionGeneralService configuracionGeneralService;

    @GetMapping
    public ResponseEntity<List<ConfiguracionGeneral>> listarParametros() {
        return ResponseEntity.ok(configuracionGeneralService.listarParametros());
    }

    @GetMapping("/{parametro}")
    public ResponseEntity<ConfiguracionGeneral> obtenerParametro(@PathVariable String parametro) {
        return configuracionGeneralService.obtenerParametro(parametro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{parametro}")
    public ResponseEntity<ConfiguracionGeneral> actualizarParametro(@PathVariable String parametro,
            @RequestBody ConfiguracionGeneral config) {
        // El usuario que actualiza debe ser seteado aquí si se requiere trazabilidad
        return ResponseEntity.ok(configuracionGeneralService.actualizarParametro(parametro, config.getValor(),
                config.getUsuarioActualiza().getIdUsuario()));
    }
}
