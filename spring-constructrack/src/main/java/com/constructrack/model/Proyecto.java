package com.constructrack.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "proyectos")
@Getter
@Setter
@NoArgsConstructor
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProyecto;

    @Column(nullable = false)
    private String nombre;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private String estado;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<Actividad> actividades;
}
