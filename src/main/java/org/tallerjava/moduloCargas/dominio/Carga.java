package org.tallerjava.moduloCargas.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity

@Table(name = "moduloCarga_CARGA")

public class Carga {
    // private LocalDate fecha; Nos parece inecesario el atributo fecha de Carga
    // porque podemos tomar cuando se realizo la carga conviertiendo el horaInicio
    // en LocalDate
    @Id
    private int id; // Podemos ver de cambiar los id para que sean longs pero por ahora da igual
                    // creo --Att Lucas

    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private float importeTotal;
    private float recargoPorDemora;
    private int porcentajeAvance; //0-100 si estadoCarga = activa
    private LocalDateTime horaEstimadaFin; //si estadoCarga = activa
    private boolean estadoCarga;
    // Carga OK o carga pendiente dado lo que devuelva el modulo de pago cuando le
    // pides que se pague la carga

    @ManyToOne
    @JoinColumn(name = "cargador_id")
    private Cargador cargador;


    @ManyToOne
    @JoinColumn(name = "cliente_cedula")
    private Cliente miCliente;
}
