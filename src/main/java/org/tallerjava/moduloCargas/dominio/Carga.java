package org.tallerjava.moduloCargas.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Duration;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private double importeTotal;
    private double recargoPorDemora;
    private int porcentajeAvance; // 0-100 si cargando = true
    private LocalDateTime horaEstimadaFin; // si cargando = true
    private boolean cargando;


    @ManyToOne
    @JoinColumn(name = "cargador_id")
    private Cargador cargador;


    @ManyToOne
    @JoinColumn(name = "cliente_cedula")
    private Cliente miCliente;

    @OneToOne
    @JoinColumn(name = "miMedioPago")
    MedioPago miPago; // Acá no me interesa que tipo de MedioPago sea

    public Carga(Cargador cargador, Cliente miCliente, MedioPago miPago) {
        this.cargador = cargador;
        this.miCliente = miCliente;
        this.miPago = miPago;
        this.horaInicio = LocalDateTime.now();
        this.cargando = true;

        // Como es necesario para calcularPorcentajeAvance lo seteo aca dependiendo si
        // el cargador es de carga rapida o lenta
        if (this.cargador.getTipoCargador().getId() == EnumTipoCargador.Rapido.getId()) {
            this.horaEstimadaFin = this.horaInicio.plusHours(2);
        } else {
            this.horaEstimadaFin = this.horaInicio.plusHours(4);
        }
    }

    public double generarTotal(double tiempoRecargo, double descuento) {

        double constantePrecioCarga = this.cargador.getCostePorHora();

        double tiempoConectado = (double) this.horaInicio.until(this.horaFin, ChronoUnit.MINUTES) / 60;
        // Como until me devuelve un long lo paso a horas ya que asumo que
        // constantePrecioCarga es el precio por horas
        double totalConectado = constantePrecioCarga * tiempoConectado;

        double totalRecargo = constantePrecioCarga * tiempoRecargo;

        this.recargoPorDemora = totalRecargo; // Supongo que si se muestra en el historial de Cargas se debe guardar aca

        this.cargando = false;

        double total = (totalConectado + totalRecargo);

        this.importeTotal = total - total*descuento;

        return this.importeTotal;
    }

    public int getPorcentajeAvance() {
        if (!this.cargando) {
            return this.porcentajeAvance;
        }

        if (this.horaEstimadaFin == null) {
            return 0;
        }

        LocalDateTime inicio = this.horaInicio;
        LocalDateTime fin = this.horaEstimadaFin;
        LocalDateTime ahora = LocalDateTime.now();

        long totalSegundos = Duration.between(inicio, fin).getSeconds();
        long transcurridos = Duration.between(inicio, ahora).getSeconds();

        if (totalSegundos <= 0) {
            return 100;
        }

        int porcentaje = (int) ((transcurridos * 100) / totalSegundos);

        return Math.max(0, Math.min(100, porcentaje));
    }
}
