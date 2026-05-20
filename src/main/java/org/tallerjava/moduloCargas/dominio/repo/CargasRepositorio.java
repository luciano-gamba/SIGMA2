package org.tallerjava.moduloCargas.dominio.repo;

import java.util.List;

import org.tallerjava.moduloCargas.dominio.*;

public interface CargasRepositorio {


    public void guardarEstacion(EstacionCarga estacionCarga);

    public void guardarCarga(Carga carga);

    public void guardarCargador(Cargador cargador);

    public List<EstacionCarga> obtenerEstaciones();

    public Cliente getCliente(String cedula);

    public void guardarCliente(Cliente cliente);

}
