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
import org.tallerjava.moduloCargas.interfase.evento.out.PublicadorEventoCarga;

@ApplicationScoped
public class ServicioCargaImpl implements ServicioCarga {

    @Inject
    private CargasRepositorio repo;

    @Inject
    private PublicadorEventoCarga evento;

    public void iniciarCarga(Cargador cargador, Cliente c, long pago) {
        // Esta operacion se expondra de manera remota con un endpoint
        cargador.setEstadoCargador(1);
        // if(!repo.encontreCliente(c)){
        // return;
        // }
        this.altaCarga(new Carga(cargador, c, new MedioPago(pago))); // Verificar que exista el cliente
        // this.altaMedioPago(pago);
    }

    public void setPorcentajeCarga(Cliente c, int porcentaje) {
        c.getCargaActiva().setPorcentajeAvance(porcentaje);
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

    public void finalizarCarga(Cargador cargador, double tiempoRecargo) {
        Carga c = cargador.getCargaActiva();
        Cliente miCliente = c.getMiCliente();

        double importeTotal = c.generarTotal(tiempoRecargo);

        cargador.setCargaActiva(null);
        cargador.setEstadoCargador(0);
        miCliente.setCargaActiva(null);
        miCliente.agregarCargaAHistorial(c);

        repo.guardarFinalizacionCarga(miCliente, c, cargador);

        evento.publicarNuevoPagoCarga(miCliente.getCedula(),importeTotal,c.getMiPago().getId());
    }
    
    @Override
    @Transactional
    public void altaEstacion(EstacionCarga estacion) {  
        repo.guardarEstacion(estacion);
    }
    
    @Override
    @Transactional
    public void altaCargador(Cargador cargador) { // Se deberia pasar a que estacion esta asociado
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
    public void altaCliente(Cliente cliente) {
        // Se llama solamente cuando el ObserverModuloCliente observa que se creo
        // un nuevo cliente en moduloClientes
        repo.guardarCliente(cliente);
    }
}
