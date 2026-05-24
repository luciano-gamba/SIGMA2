package org.tallerjava.moduloCargas.dominio.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.tallerjava.moduloCargas.dominio.*;

public interface CargasRepositorio {


    void guardarEstacion(EstacionCarga estacionCarga);

    void guardarCarga(Carga carga);

    void guardarCargador(Cargador cargador);

    void guardarCliente(Cliente cliente);

    List<EstacionCarga> obtenerEstaciones();

    Cliente getCliente(String cedula);

    Cargador getCargador(int idCargador);

    EstacionCarga getEstacion(int idEstacion);

    void guardarFinalizacionCarga(Cliente cliente, Carga carga, Cargador cargador);

    void guardarCargaAprobada(Cliente cliente, Carga carga);

    void guardarMedioPago(MedioPago medioPago);

    MedioPago getMedioPago(long pagoId);

    List<Carga> getHistorialCargas(String cedula, LocalDateTime inicio, LocalDateTime fin);
}
