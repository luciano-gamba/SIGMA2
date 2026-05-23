package org.tallerjava.moduloCargas.test;

import java.util.ArrayList;
import java.util.List;

import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.repo.CargasRepositorio;


public class CargasRepositorioFake implements CargasRepositorio{
     @Override
    public void guardarEstacion(EstacionCarga estacionCarga) {
        return;
    }

    @Override
    public void guardarCarga(Carga carga) {
        return;
    }

    @Override
    public void guardarCargador(Cargador cargador) {
        return;
    }

    @Override
    public List<EstacionCarga> obtenerEstaciones() {
        return new ArrayList<>();
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        return;
    }

    @Override
    public void guardarFinalizacionCarga(Cliente cliente, Carga carga, Cargador cargador) {
        return;
    }
    @Override
    public Cliente getCliente(String cedula){
        return new Cliente();
    }

    @Override
    public Cargador getCargador(int idCargador){
         return new Cargador();
    }

    @Override
    public EstacionCarga getEstacion(int idEstacion){
         return new EstacionCarga();
    }

    @Override
    public void guardarCargaAprobada(Cliente cliente, Carga carga){ return; }

    @Override
    public void guardarMedioPago(MedioPago medioPago){
        return;
    }

    @Override
    public MedioPago getMedioPago(long idPago){
        return new MedioPago();
    }
}
