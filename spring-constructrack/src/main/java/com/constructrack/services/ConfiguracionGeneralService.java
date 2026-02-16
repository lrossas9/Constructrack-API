package com.constructrack.services;

import com.constructrack.entities.ConfiguracionGeneral;
import com.constructrack.repositories.ConfiguracionGeneralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfiguracionGeneralService {
    private final ConfiguracionGeneralRepository configuracionGeneralRepository;

    public List<ConfiguracionGeneral> listarParametros() {
        return configuracionGeneralRepository.findAll();
    }

    public Optional<ConfiguracionGeneral> obtenerParametro(String parametro) {
        return configuracionGeneralRepository.findByParametro(parametro);
    }

    @SuppressWarnings("null")
    @Transactional
    public ConfiguracionGeneral actualizarParametro(String parametro, String valor, Long idUsuario) {
        ConfiguracionGeneral config = configuracionGeneralRepository.findByParametro(parametro)
                .orElseThrow(() -> new IllegalArgumentException("Parámetro no encontrado"));
        config.setValor(valor);
        // Se debe setear el usuario que actualiza desde el controlador
        return configuracionGeneralRepository.save(config);
    }
}
