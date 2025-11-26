package com.constructrack.repositories;

import com.constructrack.entities.Evidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Evidencia
 */
@Repository
public interface EvidenciaRepository extends JpaRepository<Evidencia, Long> {

    /**
     * Encuentra evidencias por reporte diario
     * @param idReporteDiario el ID del reporte diario
     * @return lista de evidencias del reporte
     */
    List<Evidencia> findByReporteIdReporteDiario(Long idReporteDiario);

    /**
     * Encuentra evidencias por tipo de archivo
     * @param tipoArchivo el tipo de archivo
     * @return lista de evidencias del tipo especificado
     */
    List<Evidencia> findByTipoArchivo(String tipoArchivo);
}
