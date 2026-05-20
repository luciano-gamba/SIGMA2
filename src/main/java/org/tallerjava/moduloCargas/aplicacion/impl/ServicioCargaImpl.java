package org.tallerjava.moduloCargas.aplicacion.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.tallerjava.moduloCargas.aplicacion.ServicioCarga;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.repo.CargasRepositorio;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ServicioCargaImpl implements ServicioCarga {

    @Inject
    private CargasRepositorio repo;

    public void iniciarCarga(Cargador cargador, Cliente c, MedioPago pago) {
        // Esta operacion se expondra de manera remota con un endpoint
        this.altaCarga(new Carga(cargador, c, pago));
    }

    public int verCargaActual(Cliente c) { //Esta operacion solo deberia llamarse si el cliente c tiene una carga activa
        return c.getCargaActiva().getPorcentajeAvance(); // Me puede llegar la cedula del cliente y lo busco en el repo
    }

    public List<Carga> verHistorico(Cliente c, LocalDateTime inicio, LocalDateTime fin) {
        // Me puede llegar la cedula del cliente y lo busco en el repo
        List<Carga> listaCargasCliente = c.getHistorialCargas();

        List<Carga> historicoSegunFecha = new ArrayList<>();

        for (Carga carga:listaCargasCliente){
            if(carga.getHoraFin().isAfter(inicio) && carga.getHoraFin().isBefore(fin)){
                historicoSegunFecha.add(carga);
            }
        }
        return historicoSegunFecha;
    }

    public void finalizarCarga(Carga cargaClase, double tiempoRecargo) {
        // Acá deberia calcular el recargo llamando a una operacion de la carga?
        // deberia calcular cuanto se debe pagar y llamar a medioPago con un evento que
        // este observe?

        double importeTotal = cargaClase.generarTotal(tiempoRecargo);
        // publicarPedidoImporte(cargaClase.getMiCliente(),importeTotal,cargaClase.getMiPago())
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
    
    @Override
    @Transactional
    public void altaCarga(Carga carga) {
        repo.guardarCarga(carga);
    }

    @Override
    @Transactional
    public List<EstacionCarga> obtenerEstaciones() {
        return repo.obtenerEstaciones();
    }

    @Override
    @Transactional
    public void altaCliente(Cliente cliente) { // Se llama solamente cuando el ObserverModuloCliente observa que se creo
        // un nuevo cliente en moduloClientes
        repo.guardarCliente(cliente);
    }
}
