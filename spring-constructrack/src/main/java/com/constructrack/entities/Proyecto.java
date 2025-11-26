package com.constructrack.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Proyecto - Gestiona información de proyectos de construcción
 * Cumple con RF01 - Registro de nuevos proyectos
 *
 * Atributos requeridos:
 * - Nombre del proyecto
 * - Ubicación
 * - Fecha de inicio
 * - Fecha de fin
 */
@Entity
@Table(name = "proyectos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Long idProyecto;

    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Column(nullable = false, length = 150)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotBlank(message = "La ubicación es obligatoria")
    @Column(nullable = false, length = 255)
    private String ubicacion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(length = 50)
    private String estado; // PLANIFICACIÓN, EN_EJECUCIÓN, SUSPENDIDO, FINALIZADO

    @Column(name = "presupuesto")
    private Double presupuesto;

    @Column(name = "cliente", length = 150)
    private String cliente;

    @Column(name = "contratista", length = 150)
    private String contratista;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    /**
     * Relación uno a muchos con Actividades
     */
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Actividad> actividades = new ArrayList<>();

    /**
     * Relación uno a muchos con Seguimiento
     */
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seguimiento> seguimientos = new ArrayList<>();

    /**
     * Relación uno a muchos con Reportes Diarios
     */
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReporteDiario> reportesDiarios = new ArrayList<>();
}
