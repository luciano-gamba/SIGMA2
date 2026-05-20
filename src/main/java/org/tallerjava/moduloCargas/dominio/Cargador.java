package org.tallerjava.moduloCargas.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private EnumTipoCargador tipoCargador; // Rapida o Lenta
    private boolean tieneCable; // Si el cargador tiene cable o tenes que llevar el que te vino con el auto
    private EnumTipoConector tipoConector; // Tipo 2 , CCS2, CYHAdeMO, GB/T
    private int estadoCargador; // 0 Disponible , 1 Ocupado , 2 Fuera de Servicio
    private LocalDateTime tiempoEstimadoFinalizacion; // si estadoCargador = ocupado
    private LocalDate fechaEstimadaReparacion; // si estadoCargador = fueraDeServicio
    private int potenciaMinima; // si tipoCargador = rapida
    private double costePorHora;

    @OneToOne
    @JoinColumn(name = "carga_activa_id")
    private Carga cargaActiva; // si estadoCargador = ocupado

    @OneToMany(mappedBy = "cargador")
    private List<Carga> historialCargas;

    @ManyToOne
    @JoinColumn(name = "estacion_id")
    private EstacionCarga miEstacionCarga;

    public Cargador(EnumTipoCargador tipoCargador, boolean tieneCable, EnumTipoConector tipoConector,
            double costePorHora, EstacionCarga miEstacionCarga) {
        this.tipoCargador = tipoCargador;
        this.tieneCable = tieneCable;
        this.tipoConector = tipoConector;
        this.estadoCargador = 0;
        this.costePorHora = costePorHora;
        this.miEstacionCarga = miEstacionCarga;
        this.historialCargas = new ArrayList<>();
    }

}
