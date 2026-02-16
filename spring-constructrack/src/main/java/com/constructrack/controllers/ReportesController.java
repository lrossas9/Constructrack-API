package com.constructrack.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportesController {

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard() {
        // Simulación de datos de dashboard
        return ResponseEntity.ok(Map.of(
                "avanceGeneral", 75.5,
                "presupuestoTotal", 1000000,
                "valorEjecutado", 755000,
                "saldoPendiente", 245000,
                "alertas", "Sin alertas críticas"));
    }

    @GetMapping("/avance-fechas")
    public ResponseEntity<String> avancePorFechas(@RequestParam String desde, @RequestParam String hasta) {
        // Simulación de reporte de avance por fechas
        return ResponseEntity.ok("Reporte de avance desde " + desde + " hasta " + hasta);
    }

    @GetMapping("/avance-item")
    public ResponseEntity<String> avancePorItem() {
        // Simulación de avance por ítem
        return ResponseEntity.ok("Reporte de avance por ítem");
    }

    @GetMapping("/financiero")
    public ResponseEntity<String> resumenFinanciero() {
        // Simulación de resumen financiero
        return ResponseEntity.ok("Resumen financiero del proyecto");
    }

    @GetMapping("/bitacora")
    public ResponseEntity<String> resumenBitacora() {
        // Simulación de resumen de bitácora
        return ResponseEntity.ok("Resumen de bitácora de obra");
    }

    @GetMapping("/export/excel")
    public ResponseEntity<String> exportarExcel() {
        // Simulación de exportación a Excel
        return ResponseEntity.ok("Exportación a Excel generada");
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<String> exportarPdf() {
        // Simulación de exportación a PDF
        return ResponseEntity.ok("Exportación a PDF generada");
    }
}
