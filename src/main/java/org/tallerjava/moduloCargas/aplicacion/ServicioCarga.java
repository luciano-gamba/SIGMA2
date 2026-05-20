package org.tallerjava.moduloCargas.aplicacion;

import org.tallerjava.moduloCargas.dominio.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ServicioCarga {
    public void iniciarCarga(Cargador cargador, Cliente c, MedioPago pago);

    public int verCargaActual(Cliente c);

    public void setPorcentajeCarga(Cliente c, int porcentaje);

    public List<Carga> verHistorico(Cliente c, LocalDateTime inicio, LocalDateTime fin);

    public void finalizarCarga(Cargador cargador, double tiempoRecargo);

    public void altaEstacion(EstacionCarga estacion);
    public void altaCargador(Cargador cargador);

    public void altaCarga(Carga carga);
    public List<EstacionCarga> obtenerEstaciones();

    public void altaCliente(Cliente cliente);
}
