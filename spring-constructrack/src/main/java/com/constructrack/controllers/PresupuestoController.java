package com.constructrack.controllers;

import com.constructrack.entities.Presupuesto;
import com.constructrack.entities.PresupuestoItem;
import com.constructrack.entities.Proyecto;
import com.constructrack.entities.Usuario;
import com.constructrack.dtos.PresupuestoDTO;
import com.constructrack.services.PresupuestoService;
import com.constructrack.repositories.ProyectoRepository;
import com.constructrack.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presupuestos")
@RequiredArgsConstructor
public class PresupuestoController {
    private final PresupuestoService presupuestoService;
    private final UsuarioRepository usuarioRepository;
    private final ProyectoRepository proyectoRepository;

    @GetMapping
    public ResponseEntity<List<Presupuesto>> listarPresupuestos() {
        return ResponseEntity.ok(presupuestoService.listarPresupuestos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Presupuesto> obtenerPresupuesto(@PathVariable Long id) {
        return presupuestoService.obtenerPresupuesto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @SuppressWarnings("null")
    @PreAuthorize("hasAnyRole('RESIDENTE_OBRA','ADMINISTRADOR_SISTEMA')")
    @PostMapping
    public ResponseEntity<?> crearPresupuesto(@RequestBody @jakarta.validation.Valid PresupuestoDTO dto,
            org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        // Convertir DTO a entidad
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuarioCreador()).orElse(null);
        Proyecto proyecto = proyectoRepository.findById(dto.getIdProyecto()).orElse(null);
        if (usuario == null || proyecto == null) {
            return ResponseEntity.badRequest().body("Usuario o proyecto no encontrado");
        }
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setNombre(dto.getNombre());
        presupuesto.setDescripcion(dto.getDescripcion());
        presupuesto.setUsuarioCreador(usuario);
        presupuesto.setProyecto(proyecto);
        // Convertir ítems
        List<PresupuestoItem> items = dto.getItems().stream().map(itemDto -> {
            PresupuestoItem item = new PresupuestoItem();
            item.setItem(itemDto.getItem());
            item.setDescripcion(itemDto.getDescripcion());
            item.setUnidad(itemDto.getUnidad());
            item.setCantidad(itemDto.getCantidad());
            item.setValorUnitario(itemDto.getValorUnitario());
            item.setPresupuesto(presupuesto);
            return item;
        }).toList();
        presupuesto.setItems(items);
        return ResponseEntity.ok(presupuestoService.crearPresupuesto(presupuesto));
    }

    @SuppressWarnings("null")
    @PreAuthorize("hasAnyRole('RESIDENTE_OBRA','ADMINISTRADOR_SISTEMA')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPresupuesto(@PathVariable Long id,
            @RequestBody @jakarta.validation.Valid PresupuestoDTO dto,
            org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuarioCreador()).orElse(null);
        Proyecto proyecto = proyectoRepository.findById(dto.getIdProyecto()).orElse(null);
        if (usuario == null || proyecto == null) {
            return ResponseEntity.badRequest().body("Usuario o proyecto no encontrado");
        }
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setIdPresupuesto(id);
        presupuesto.setNombre(dto.getNombre());
        presupuesto.setDescripcion(dto.getDescripcion());
        presupuesto.setUsuarioCreador(usuario);
        presupuesto.setProyecto(proyecto);
        List<PresupuestoItem> items = dto.getItems().stream().map(itemDto -> {
            PresupuestoItem item = new PresupuestoItem();
            item.setItem(itemDto.getItem());
            item.setDescripcion(itemDto.getDescripcion());
            item.setUnidad(itemDto.getUnidad());
            item.setCantidad(itemDto.getCantidad());
            item.setValorUnitario(itemDto.getValorUnitario());
            item.setPresupuesto(presupuesto);
            return item;
        }).toList();
        presupuesto.setItems(items);
        return ResponseEntity.ok(presupuestoService.actualizarPresupuesto(presupuesto));
    }

    @PreAuthorize("hasAnyRole('RESIDENTE_OBRA','ADMINISTRADOR_SISTEMA')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPresupuesto(@PathVariable Long id) {
        presupuestoService.eliminarPresupuesto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<PresupuestoItem>> listarItems(@PathVariable Long id) {
        return ResponseEntity.ok(presupuestoService.listarItems(id));
    }

    @PreAuthorize("hasAnyRole('RESIDENTE_OBRA','ADMINISTRADOR_SISTEMA')")
    @PostMapping("/{id}/items")
    public ResponseEntity<PresupuestoItem> agregarItem(@PathVariable Long id, @RequestBody PresupuestoItem item) {
        // Asignar el presupuesto al item
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setIdPresupuesto(id);
        item.setPresupuesto(presupuesto);
        return ResponseEntity.ok(presupuestoService.agregarItem(item));
    }

    @PreAuthorize("hasAnyRole('RESIDENTE_OBRA','ADMINISTRADOR_SISTEMA')")
    @PutMapping("/items/{itemId}")
    public ResponseEntity<PresupuestoItem> actualizarItem(@PathVariable Long itemId,
            @RequestBody PresupuestoItem item) {
        item.setIdItem(itemId);
        return ResponseEntity.ok(presupuestoService.actualizarItem(item));
    }
}
