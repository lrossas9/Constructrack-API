package com.constructrack.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evidencias")
@Getter
@Setter
@NoArgsConstructor
public class Evidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvidencia;

    // guardamos ruta de fichero en disco; puede adaptarse para BLOB
    private String archivo;

    private String tipoArchivo;

    @ManyToOne
    @JoinColumn(name = "idReporte")
    private Reporte reporte;
}
