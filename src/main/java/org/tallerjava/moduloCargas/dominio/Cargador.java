package org.tallerjava.moduloCargas.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity

@Table(name = "moduloCarga_CARGADOR")

public class Cargador {

    @Id
    private int id;

    // private TipoCargador tipoCargador; // Potencia de carga
    private boolean tieneCable; // Si el cargador tiene cable o tenes que llevar el que te vino con el auto
    // private TipoConector tipoConector; // Tipo 2 , CCS2, CYHAdeMO, GB/T
    private int estadoCargador; // 0 Disponible , 1 Ocupado , 2 Fuera de Servicio
    private LocalDateTime tiempoEstimadoFinalizacion; // si estadoCargador = ocupado
    private LocalDate fechaEstimadaReparacion; // si estadoCargador = fueraDeServicio
    private int potenciaMinima; // si tipoCargador = rapida

    @OneToOne
    @JoinColumn(name = "carga_activa_id")
    private Carga cargaActiva; // si estadoCargador = ocupado

    @OneToMany(mappedBy = "cargador")
    private List<Carga> historialCargas;

    @ManyToOne
    @JoinColumn(name = "estacion_id")
    private EstacionCarga miEstacionCarga;
}
