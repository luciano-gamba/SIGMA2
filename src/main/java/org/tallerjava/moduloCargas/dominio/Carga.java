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
    private int porcentajeAvance; // 0-100 si cargando = true
    private LocalDateTime horaEstimadaFin; // si cargando = true
    private boolean cargando;
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
        this.cargando = true;
    }

    public double generarTotal(double tiempoRecargo) {
        // Asumo que esta sera la operacion encargada de todo lo que tiene que ver con
        // finalizarCarga desde tomar cual es la hora fin,
        // marcar que ya no esta cargando y tal vez en
        // ServicioCargaImpl hacer que el cliente deje de tener Asignada la Carga como
        // CargaActiva y se agregue en su historial de cargas
        double constantePrecioCarga = this.cargador.getCostePorHora(); // Preguntar a profe

        // Aca setearia que es la hora fin?

        double tiempoConectado = this.horaInicio.until(this.horaFin, ChronoUnit.MINUTES) / 60;
        // Como until me devuelve un long lo paso a horas ya que asumo que
        // constantePrecioCarga es el precio por horas
        double totalConectado = constantePrecioCarga * tiempoConectado;

        double totalRecargo = constantePrecioCarga * tiempoRecargo;

        this.recargoPorDemora = totalRecargo; // Supongo que si se muestra en el historial de Cargas se debe guardar aca

        this.cargando = false;

        this.importeTotal = totalConectado + totalRecargo;

        return this.importeTotal;
    }

}
