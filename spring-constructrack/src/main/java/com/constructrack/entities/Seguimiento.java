package com.constructrack.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad Seguimiento - Registra el avance de obra en los proyectos
 * Cumple con RF02 - Consultar avance de obra en tiempo real
 *
 * Atributos requeridos:
 * - Avance_Porcentaje (DECIMAL(3,2)) - Rango 0.00 a 100.00
 * - Id_Proyecto (FK)
 * - Observaciones
 */
@Entity
@Table(name = "seguimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    private Long idSeguimiento;

    @NotNull(message = "El porcentaje de avance es obligatorio")
    @DecimalMin(value = "0.0", message = "El avance debe ser mayor o igual a 0")
    @DecimalMax(value = "100.0", message = "El avance no puede exceder 100")
    @Column(name = "avance_porcentaje", nullable = false, precision = 5, scale = 2)
    private BigDecimal avancePorcentaje;

    @Column(name = "fecha_seguimiento", nullable = false)
    private LocalDate fechaSeguimiento;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(length = 50)
    private String estado; // EN_TIEMPO, ATRASADO, ADELANTADO

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    /**
     * Relación muchos a uno con Proyecto
     * Un proyecto puede tener múltiples seguimientos
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    /**
     * Relación muchos a uno con Usuario (usuario que registra el seguimiento)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuarioRegistro;
}
