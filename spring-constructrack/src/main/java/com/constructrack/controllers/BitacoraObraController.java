package com.constructrack.controllers;

import com.constructrack.entities.BitacoraObra;
import com.constructrack.services.BitacoraObraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
@RequiredArgsConstructor
public class BitacoraObraController {
    private final BitacoraObraService bitacoraObraService;

    @GetMapping
    public ResponseEntity<List<BitacoraObra>> listarBitacoras() {
        return ResponseEntity.ok(bitacoraObraService.listarBitacoras());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BitacoraObra> obtenerBitacora(@PathVariable Long id) {
        return bitacoraObraService.obtenerBitacora(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<BitacoraObra>> listarPorProyecto(@PathVariable Long idProyecto) {
        return ResponseEntity.ok(bitacoraObraService.listarPorProyecto(idProyecto));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<BitacoraObra>> listarPorFecha(@PathVariable String fecha) {
        return ResponseEntity.ok(bitacoraObraService.listarPorFecha(LocalDate.parse(fecha)));
    }

    @PreAuthorize("hasAnyRole('RESIDENTE_OBRA','SUPERVISOR')")
    @PostMapping
    public ResponseEntity<BitacoraObra> crearBitacora(@RequestBody BitacoraObra bitacora) {
        return ResponseEntity.ok(bitacoraObraService.crearBitacora(bitacora));
    }

    @PreAuthorize("hasAnyRole('RESIDENTE_OBRA','SUPERVISOR')")
    @PutMapping("/{id}")
    public ResponseEntity<BitacoraObra> actualizarBitacora(@PathVariable Long id, @RequestBody BitacoraObra bitacora) {
        bitacora.setIdBitacora(id);
        return ResponseEntity.ok(bitacoraObraService.actualizarBitacora(bitacora));
    }

    @PreAuthorize("hasAnyRole('RESIDENTE_OBRA','SUPERVISOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBitacora(@PathVariable Long id) {
        bitacoraObraService.eliminarBitacora(id);
        return ResponseEntity.noContent().build();
    }
}
