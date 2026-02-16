package com.constructrack.controllers;

import com.constructrack.entities.AvanceDiario;
import com.constructrack.services.AvanceDiarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/avance")
@RequiredArgsConstructor
public class AvanceDiarioController {
    private final AvanceDiarioService avanceDiarioService;

    @GetMapping
    public ResponseEntity<List<AvanceDiario>> listarAvances() {
        return ResponseEntity.ok(avanceDiarioService.listarAvances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvanceDiario> obtenerAvance(@PathVariable Long id) {
        return avanceDiarioService.obtenerAvance(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<AvanceDiario>> listarPorProyecto(@PathVariable Long idProyecto) {
        return ResponseEntity.ok(avanceDiarioService.listarPorProyecto(idProyecto));
    }

    @GetMapping("/actividad/{idActividad}")
    public ResponseEntity<List<AvanceDiario>> listarPorActividad(@PathVariable Long idActividad) {
        return ResponseEntity.ok(avanceDiarioService.listarPorActividad(idActividad));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<AvanceDiario>> listarPorFecha(@PathVariable String fecha) {
        return ResponseEntity.ok(avanceDiarioService.listarPorFecha(LocalDate.parse(fecha)));
    }

    @PreAuthorize("hasAnyRole('RESIDENTE_OBRA','SUPERVISOR')")
    @PostMapping
    public ResponseEntity<AvanceDiario> crearAvance(@RequestBody AvanceDiario avance) {
        return ResponseEntity.ok(avanceDiarioService.crearAvance(avance));
    }

    @PreAuthorize("hasAnyRole('RESIDENTE_OBRA','SUPERVISOR')")
    @PutMapping("/{id}")
    public ResponseEntity<AvanceDiario> actualizarAvance(@PathVariable Long id, @RequestBody AvanceDiario avance) {
        avance.setIdAvance(id);
        return ResponseEntity.ok(avanceDiarioService.actualizarAvance(avance));
    }

    @PreAuthorize("hasAnyRole('RESIDENTE_OBRA','SUPERVISOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAvance(@PathVariable Long id) {
        avanceDiarioService.eliminarAvance(id);
        return ResponseEntity.noContent().build();
    }
}
