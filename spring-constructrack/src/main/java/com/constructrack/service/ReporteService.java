package com.constructrack.service;

import com.constructrack.model.Actividad;
import com.constructrack.model.Evidencia;
import com.constructrack.model.Reporte;
import com.constructrack.model.Usuario;
import com.constructrack.repository.EvidenciaRepository;
import com.constructrack.repository.ReporteRepository;
import com.constructrack.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private EvidenciaRepository evidenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Reporte crearReporte(Usuario usuario, Actividad actividad, String observaciones, List<MultipartFile> files) throws IOException {
        Reporte reporte = new Reporte();
        reporte.setFecha(LocalDateTime.now());
        reporte.setObservaciones(observaciones);
        reporte.setUsuario(usuario);
        reporte.setActividad(actividad);

        Reporte saved = reporteRepository.save(reporte);

        if (files != null && !files.isEmpty()) {
            Path base = Paths.get(uploadDir);
            if (!Files.exists(base)) Files.createDirectories(base);

            List<Evidencia> evidencias = new ArrayList<>();
            for (MultipartFile mf : files) {
                String fname = System.currentTimeMillis() + "_" + mf.getOriginalFilename();
                Path dest = base.resolve(fname);
                mf.transferTo(dest.toFile());

                Evidencia e = new Evidencia();
                e.setArchivo(dest.toString());
                e.setTipoArchivo(mf.getContentType());
                e.setReporte(saved);
                evidencias.add(evidenciaRepository.save(e));
            }
            saved = reporteRepository.findById(saved.getIdReporte()).orElse(saved);
        }

        return saved;
    }

    public Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
