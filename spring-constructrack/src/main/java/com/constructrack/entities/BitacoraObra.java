package com.constructrack.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bitacoras_obra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BitacoraObra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bitacora")
    private Long idBitacora;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 50)
    private String numeroNota;

    @Column(columnDefinition = "TEXT")
    private String descripcionTrabajos;
    private String frentes;
    private String clima;
    private String personal;
    private String maquinaria;
    private String equipos;
    private String materiales;
    private String pruebas;
    private String incidentes;
    private String cambios;
    private String accidentes;
    private String suspensiones;
    private String cierre;
    private String actas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private Boolean activo = true;
}
