package com.constructrack.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad ReporteDiario - Registra reportes diarios de la obra
 * Incluye información sobre clima, observaciones, trabajadores y novedades
 *
 * Atributos requeridos:
 * - Fecha
 * - Clima
 * - Observaciones
 * - Id_Usuario (FK) - Usuario que registra el reporte
 * - Id_Actividad (FK) - Actividad asociada
 */
@Entity
@Table(name = "reportes_diarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte_diario")
    private Long idReporteDiario;

    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotBlank(message = "La información del clima es obligatoria")
    @Column(nullable = false, length = 100)
    private String clima;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "cantidad_trabajadores")
    private Integer cantidadTrabajadores;

    @Column(name = "horas_trabajadas")
    private Double horasTrabajadas;

    @Column(columnDefinition = "TEXT")
    private String novedades;

    @Column(columnDefinition = "TEXT")
    private String incidentes;

    @Column(name = "materiales_utilizados", columnDefinition = "TEXT")
    private String materialesUtilizados;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    /**
     * Relación muchos a uno con Usuario
     * El usuario es quien registra el reporte
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Relación muchos a uno con Proyecto
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    /**
     * Relación muchos a uno con Actividad
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

    /**
     * Relación uno a muchos con Evidencias (fotos/archivos)
     */
    @OneToMany(mappedBy = "reporte", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Evidencia> evidencias = new ArrayList<>();
}
