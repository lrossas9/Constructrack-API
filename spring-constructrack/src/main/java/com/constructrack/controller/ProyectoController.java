package com.constructrack.controller;

import com.constructrack.model.Actividad;
import com.constructrack.model.Proyecto;
import com.constructrack.service.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<Proyecto>> listar() {
        return ResponseEntity.ok(proyectoService.listarProyectos());
    }

    @PostMapping
    public ResponseEntity<Proyecto> crear(@Valid @RequestBody Proyecto proyecto) {
        Proyecto creado = proyectoService.crearProyecto(proyecto);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/{id}/actividades")
    public ResponseEntity<List<Actividad>> actividades(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.getActividadesByProyectoId(id));
    }
}
