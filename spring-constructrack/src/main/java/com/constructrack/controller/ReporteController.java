package com.constructrack.controller;

import com.constructrack.model.Actividad;
import com.constructrack.model.Reporte;
import com.constructrack.model.Usuario;
import com.constructrack.repository.ActividadRepository;
import com.constructrack.service.ReporteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ActividadRepository actividadRepository;

    @PostMapping
    public ResponseEntity<?> crearReporte(@RequestParam Long usuarioId,
                                         @RequestParam Long actividadId,
                                         @RequestParam(required = false) String observaciones,
                                         @RequestParam(required = false) List<MultipartFile> files,
                                         HttpServletRequest request) {
        try {
            Usuario u = reporteService.findUsuarioById(usuarioId);
            if (u == null) return ResponseEntity.badRequest().body("Usuario no encontrado");

            Actividad a = actividadRepository.findById(actividadId).orElse(null);
            if (a == null) return ResponseEntity.badRequest().body("Actividad no encontrada");

            Reporte r = reporteService.crearReporte(u, a, observaciones, files);
            return ResponseEntity.ok(r);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error guardando archivos");
        }
    }
}
