package com.constructrack.services;

import com.constructrack.entities.BitacoraObra;
import com.constructrack.repositories.BitacoraObraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BitacoraObraService {
    private final BitacoraObraRepository bitacoraObraRepository;

    public List<BitacoraObra> listarBitacoras() {
        return bitacoraObraRepository.findByActivoTrue();
    }

    public Optional<BitacoraObra> obtenerBitacora(Long id) {
        return bitacoraObraRepository.findById(id);
    }

    public List<BitacoraObra> listarPorProyecto(Long idProyecto) {
        return bitacoraObraRepository.findByProyectoIdProyecto(idProyecto);
    }

    public List<BitacoraObra> listarPorFecha(LocalDate fecha) {
        return bitacoraObraRepository.findByFecha(fecha);
    }

    @SuppressWarnings("null")
    @Transactional
    public BitacoraObra crearBitacora(BitacoraObra bitacora) {
        return bitacoraObraRepository.save(bitacora);
    }

    @SuppressWarnings("null")
    @Transactional
    public BitacoraObra actualizarBitacora(BitacoraObra bitacora) {
        return bitacoraObraRepository.save(bitacora);
    }

    @SuppressWarnings("null")
    @Transactional
    public void eliminarBitacora(Long id) {
        bitacoraObraRepository.findById(id).ifPresent(b -> {
            b.setActivo(false);
            bitacoraObraRepository.save(b);
        });
    }
}
