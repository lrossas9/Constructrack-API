package com.constructrack.services;

import com.constructrack.entities.AuditLog;
import com.constructrack.repositories.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;

    public List<AuditLog> listarLogs() {
        return auditLogRepository.findAll();
    }

    @SuppressWarnings("null")
    public Optional<AuditLog> obtenerLog(Long id) {
        return auditLogRepository.findById(id);
    }

    public List<AuditLog> listarPorUsuario(Long idUsuario) {
        return auditLogRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public List<AuditLog> listarPorModulo(String modulo) {
        return auditLogRepository.findByModulo(modulo);
    }

    @SuppressWarnings("null")
    public AuditLog guardarLog(AuditLog log) {
        return auditLogRepository.save(log);
    }
}
