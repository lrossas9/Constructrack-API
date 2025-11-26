package com.constructrack.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entidad Evidencia - Almacena información de archivos y fotos adjuntos a reportes
 */
@Entity
@Table(name = "evidencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evidencia")
    private Long idEvidencia;

    @NotBlank(message = "El nombre del archivo es obligatorio")
    @Column(nullable = false, length = 255)
    private String nombreArchivo;

    @Column(name = "ruta_archivo", nullable = false, columnDefinition = "TEXT")
    private String rutaArchivo;

    @NotBlank(message = "El tipo de archivo es obligatorio")
    @Column(nullable = false, length = 50)
    private String tipoArchivo; // FOTO, DOCUMENTO, VIDEO, OTRO

    @Column(name = "tamaño_bytes")
    private Long tamanioBytes;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @CreationTimestamp
    @Column(name = "fecha_carga", nullable = false, updatable = false)
    private LocalDateTime fechaCarga;

    /**
     * Relación muchos a uno con ReporteDiario
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reporte_diario", nullable = false)
    private ReporteDiario reporte;
}
