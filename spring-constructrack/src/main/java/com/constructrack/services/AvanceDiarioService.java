package com.constructrack.services;

import com.constructrack.entities.AvanceDiario;
import com.constructrack.repositories.AvanceDiarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvanceDiarioService {
    private final AvanceDiarioRepository avanceDiarioRepository;

    public List<AvanceDiario> listarAvances() {
        return avanceDiarioRepository.findByActivoTrue();
    }

    public Optional<AvanceDiario> obtenerAvance(Long id) {
        return avanceDiarioRepository.findById(id);
    }

    public List<AvanceDiario> listarPorProyecto(Long idProyecto) {
        return avanceDiarioRepository.findByProyectoIdProyecto(idProyecto);
    }

    public List<AvanceDiario> listarPorActividad(Long idActividad) {
        return avanceDiarioRepository.findByActividadIdActividad(idActividad);
    }

    public List<AvanceDiario> listarPorFecha(LocalDate fecha) {
        return avanceDiarioRepository.findByFecha(fecha);
    }

    @SuppressWarnings("null")
    @Transactional
    public AvanceDiario crearAvance(AvanceDiario avance) {
        return avanceDiarioRepository.save(avance);
    }

    @SuppressWarnings("null")
    @Transactional
    public AvanceDiario actualizarAvance(AvanceDiario avance) {
        return avanceDiarioRepository.save(avance);
    }

    @SuppressWarnings("null")
    @Transactional
    public void eliminarAvance(Long id) {
        avanceDiarioRepository.findById(id).ifPresent(a -> {
            a.setActivo(false);
            avanceDiarioRepository.save(a);
        });
    }
}
