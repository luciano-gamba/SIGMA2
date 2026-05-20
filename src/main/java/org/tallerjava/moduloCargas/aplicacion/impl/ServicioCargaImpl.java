package org.tallerjava.moduloCargas.aplicacion.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.tallerjava.moduloCargas.aplicacion.ServicioCarga;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.repo.CargasRepositorio;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

public class ServicioCargaImpl implements ServicioCarga {

    @Inject
    private CargasRepositorio repo;

    public void iniciarCarga(Cliente c, MedioPago pago) {

    }

    public int verCargaActual(Cliente c) { //Esta operacion solo deberia llamarse si el cliente c tiene una carga activa
        return c.getCargaActiva().getPorcentajeAvance();
    }

    public List<Carga> verHistorico(Cliente c, LocalDateTime inicio, LocalDateTime fin) {
        List<Carga> listaCargasCliente = c.getHistorialCargas();

        List<Carga> historicoSegunFecha = new ArrayList<>();

        for (Carga carga:listaCargasCliente){
            if(carga.getHoraFin().isAfter(inicio) && carga.getHoraFin().isBefore(fin)){
                historicoSegunFecha.add(carga);
            }
        }
        return historicoSegunFecha;
    }

    public void finalizarCarga(Cargador cargador, int carga, LocalDateTime recargo) {

    }
    
    @Override
    @Transactional
    public void altaEstacion(EstacionCarga estacion) {  
        repo.guardarEstacion(estacion);
    }
    
    @Override
    @Transactional
    public void altaCargador(Cargador cargador) { 
        repo.guardarCargador(cargador);
    }
    
    public void altaCarga(Carga carga, Cliente cliente){
        repo.guardarCarga(carga, cliente);
    }

    @Override
    @Transactional
    public List<EstacionCarga> obtenerEstaciones() {

        return repo.obtenerEstaciones();
    }
}
