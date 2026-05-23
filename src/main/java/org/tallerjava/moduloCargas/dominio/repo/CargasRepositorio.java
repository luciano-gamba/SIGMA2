package org.tallerjava.moduloCargas.dominio.repo;

import java.util.List;

import org.tallerjava.moduloCargas.dominio.*;

public interface CargasRepositorio {


    public void guardarEstacion(EstacionCarga estacionCarga);

    public void guardarCarga(Carga carga);

    public void guardarCargador(Cargador cargador);

    public void guardarCliente(Cliente cliente);

    public List<EstacionCarga> obtenerEstaciones();

    public Cliente getCliente(String cedula);

    public Cargador getCargador(int idCargador);

    public EstacionCarga getEstacion(int idEstacion);

    public void guardarFinalizacionCarga(Cliente cliente, Carga carga, Cargador cargador);

    void guardarCargaAprobada(Cliente cliente, Carga carga);

    void guardarMedioPago(MedioPago medioPago);

    MedioPago getMedioPago(long pagoId);
}
