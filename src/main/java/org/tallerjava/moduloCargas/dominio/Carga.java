package org.tallerjava.moduloCargas.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Podemos ver de cambiar los id para que sean longs pero por ahora da igual
                    // creo --Att Lucas

    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private double importeTotal;
    private double recargoPorDemora;
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

    @OneToOne
    @JoinColumn(name = "miMedioPago")
    MedioPago miPago; // Acá no creo que me interese que tipo de MedioPago sea

    public Carga(Cargador cargador, Cliente miCliente, MedioPago miPago) {
        this.cargador = cargador;
        this.miCliente = miCliente;
        this.miPago = miPago;
        this.horaInicio = LocalDateTime.now();
        this.estadoCarga = true;
    }

    public double generarTotal(double tiempoRecargo) {
        double constantePrecioCarga = this.cargador.getCostePorHora(); // Preguntar a profe

        double tiempoConectado = this.horaInicio.until(this.horaFin, ChronoUnit.MINUTES) / 60;
        // Como until me devuelve un long lo paso a horas ya que asumo que
        // constantePrecioCarga es el precio por horas
        double totalConectado = constantePrecioCarga * tiempoConectado;

        double totalRecargo = constantePrecioCarga * tiempoRecargo;

        this.importeTotal = totalConectado + totalRecargo;

        return this.importeTotal;
    }

}
