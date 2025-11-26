package com.constructrack.services;

import com.constructrack.entities.Evidencia;
import com.constructrack.entities.ReporteDiario;
import com.constructrack.repositories.EvidenciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de Evidencia - Gestiona archivos y fotos adjuntos a reportes
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EvidenciaService {

    private final EvidenciaRepository evidenciaRepository;
    private final ReporteDiarioService reporteDiarioService;

    /**
     * Registra una nueva evidencia (foto/archivo) para un reporte
     *
     * @param idReporteDiario ID del reporte
     * @param nombreArchivo nombre del archivo
     * @param rutaArchivo ruta donde se almacena el archivo
     * @param tipoArchivo tipo de archivo (FOTO, DOCUMENTO, VIDEO, OTRO)
     * @param tamanioBytes tamaño del archivo en bytes
     * @param descripcion descripción de la evidencia
     * @return Evidencia creada
     */
    public Evidencia registrarEvidencia(Long idReporteDiario, String nombreArchivo, String rutaArchivo,
                                       String tipoArchivo, Long tamanioBytes, String descripcion) {
        log.info("Registrando evidencia para reporte: {}", idReporteDiario);

        ReporteDiario reporte = reporteDiarioService.obtenerReportePorId(idReporteDiario);

        Evidencia nuevaEvidencia = new Evidencia();
        nuevaEvidencia.setNombreArchivo(nombreArchivo);
        nuevaEvidencia.setRutaArchivo(rutaArchivo);
        nuevaEvidencia.setTipoArchivo(tipoArchivo);
        nuevaEvidencia.setTamanioBytes(tamanioBytes);
        nuevaEvidencia.setDescripcion(descripcion);
        nuevaEvidencia.setReporte(reporte);

        Evidencia evidenciaGuardada = evidenciaRepository.save(nuevaEvidencia);
        log.info("Evidencia registrada exitosamente: {}", evidenciaGuardada.getIdEvidencia());

        return evidenciaGuardada;
    }

    /**
     * Obtiene todas las evidencias de un reporte
     *
     * @param idReporteDiario ID del reporte
     * @return lista de evidencias del reporte
     */
    @Transactional(readOnly = true)
    public List<Evidencia> obtenerEvidenciasPorReporte(Long idReporteDiario) {
        reporteDiarioService.obtenerReportePorId(idReporteDiario); // Validar que el reporte existe
        return evidenciaRepository.findByReporteIdReporteDiario(idReporteDiario);
    }

    /**
     * Obtiene una evidencia por su ID
     *
     * @param idEvidencia ID de la evidencia
     * @return Evidencia si existe
     * @throws IllegalArgumentException si no existe
     */
    @Transactional(readOnly = true)
    public Evidencia obtenerEvidenciaPorId(Long idEvidencia) {
        return evidenciaRepository.findById(idEvidencia)
                .orElseThrow(() -> new IllegalArgumentException("Evidencia no encontrada"));
    }

    /**
     * Obtiene evidencias por tipo de archivo
     *
     * @param tipoArchivo tipo de archivo a buscar
     * @return lista de evidencias del tipo especificado
     */
    @Transactional(readOnly = true)
    public List<Evidencia> obtenerEvidenciasPorTipo(String tipoArchivo) {
        return evidenciaRepository.findByTipoArchivo(tipoArchivo);
    }

    /**
     * Elimina una evidencia
     *
     * @param idEvidencia ID de la evidencia a eliminar
     */
    public void eliminarEvidencia(Long idEvidencia) {
        log.info("Eliminando evidencia: {}", idEvidencia);

        obtenerEvidenciaPorId(idEvidencia); // Validar que existe
        evidenciaRepository.deleteById(idEvidencia);

        log.info("Evidencia eliminada: {}", idEvidencia);
    }
}
