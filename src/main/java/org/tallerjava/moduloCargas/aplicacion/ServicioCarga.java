package org.tallerjava.moduloCargas.aplicacion;

import org.tallerjava.moduloCargas.dominio.*;

import java.time.LocalDateTime;
import java.util.List;

public interface ServicioCarga {
    void iniciarCarga(int idCargador, String cedula, long pago);

    int verCargaActual(String cedula);

    void setPorcentajeCarga(String cedula, int porcentaje);

    List<Carga> verHistorico(String cedula, LocalDateTime inicio, LocalDateTime fin);

    void finalizarCarga(int idCar, double tiempoRecargo);

    void altaEstacion(EstacionCarga estacion);

    void altaCargador(Cargador cargador, int idEstacionCarga);

    void altaCarga(Carga carga);

    List<EstacionCarga> obtenerEstaciones();

    void altaCliente(Cliente cliente);

    void cargaAprovada(boolean aceptado, String cedula);
}
