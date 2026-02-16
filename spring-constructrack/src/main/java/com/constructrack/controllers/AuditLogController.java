package com.constructrack.controllers;

import com.constructrack.entities.AuditLog;
import com.constructrack.services.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRADOR_SISTEMA')")
public class AuditLogController {
    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLog>> listarLogs() {
        return ResponseEntity.ok(auditLogService.listarLogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLog> obtenerLog(@PathVariable Long id) {
        return auditLogService.obtenerLog(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<AuditLog>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(auditLogService.listarPorUsuario(idUsuario));
    }

    @GetMapping("/modulo/{modulo}")
    public ResponseEntity<List<AuditLog>> listarPorModulo(@PathVariable String modulo) {
        return ResponseEntity.ok(auditLogService.listarPorModulo(modulo));
    }
}
