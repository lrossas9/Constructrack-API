package com.constructrack.service;

import com.constructrack.model.Actividad;
import com.constructrack.model.Proyecto;
import com.constructrack.repository.ActividadRepository;
import com.constructrack.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    public Proyecto crearProyecto(Proyecto p) {
        return proyectoRepository.save(p);
    }

    public List<Actividad> getActividadesByProyectoId(Long idProyecto) {
        return actividadRepository.findByProyectoIdProyecto(idProyecto);
    }
}
