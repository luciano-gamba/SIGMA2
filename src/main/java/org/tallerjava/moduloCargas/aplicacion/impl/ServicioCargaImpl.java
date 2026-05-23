package org.tallerjava.moduloCargas.aplicacion.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.tallerjava.moduloCargas.aplicacion.ServicioCarga;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.dto.EstacionCargaDTO;
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

    @Override
    @Transactional
    public void iniciarCarga(int idCargador, String cedula, long pago) {
        Cargador cargador = repo.getCargador(idCargador);
        Cliente c = repo.getCliente(cedula);
        MedioPago medioPago = repo.getMedioPago(pago);

        if(c.getCargaPendiente() != null || c.getCargaActiva() != null){
            //si tiene alguna carga pendiente de pagar o una carga activa, no le permite iniciar una carga nueva hasta que la pague/termine la actual
            throw new IllegalArgumentException("El cliente tiene una carga activa/pendiente.");
        }
        // Esta operacion se expondra de manera remota con un endpoint
        cargador.setEstadoCargador(1);
        // if(!repo.encontreCliente(c)){
        // return;
        // }
        this.altaCarga(new Carga(cargador, c, medioPago)); // Verificar que exista el cliente
        // this.altaMedioPago(pago);
    }

    public int verCargaActual(String cedula) { //Esta operacion solo deberia llamarse si el cliente c tiene una carga activa
        Cliente c = repo.getCliente(cedula);
        if(c == null){
            throw new IllegalArgumentException("El cliente no existe.");
        }
        if(c.getCargaActiva() == null){
            throw new IllegalArgumentException("El cliente no tiene una carga activa.");
        }else{
            return c.getCargaActiva().getPorcentajeAvance();
        }
    }

    public List<Carga> verHistorico(String cedula, LocalDateTime inicio, LocalDateTime fin) {
        Cliente c = repo.getCliente(cedula);

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

    public void finalizarCarga(int idCar, double tiempoRecargo) {
        Cargador cargador = repo.getCargador(idCar);
        if(cargador == null){
            throw new IllegalArgumentException("El cargador no existe.");
        }

        Carga c = cargador.getCargaActiva();
        if(c == null){
            throw new IllegalArgumentException("El cargador no tiene una carga activa.");
        }
        Cliente miCliente = c.getMiCliente();


        double importeTotal = c.generarTotal(tiempoRecargo, miCliente.getDescuento());

        c.setHoraFin(LocalDateTime.now());
        c.setPorcentajeAvance(c.getPorcentajeAvance());
        c.setCargando(false);

        cargador.setCargaActiva(null);
        cargador.setEstadoCargador(0);
        miCliente.setCargaActiva(null);
        miCliente.setCargaPendiente(c);

        repo.guardarFinalizacionCarga(miCliente, c, cargador);

        evento.publicarNuevoPagoCarga(miCliente.getCedula(),importeTotal,c.getMiPago().getId());
    }

    @Override
    public void reintentarPago(String cedula){
        Cliente miCliente = repo.getCliente(cedula);
        if(miCliente == null){
            throw new IllegalArgumentException("El cliente no existe.");
        }
        Carga c = miCliente.getCargaPendiente();
        if(c == null){
            throw new IllegalArgumentException("El cliente no tiene una carga pendiente.");
        }
        evento.publicarNuevoPagoCarga(miCliente.getCedula(),c.getImporteTotal(),c.getMiPago().getId());
    }
    
    @Override
    @Transactional
    public void altaEstacion(EstacionCarga estacion) {  
        repo.guardarEstacion(estacion);
    }
    
    @Override
    @Transactional
    public void altaCargador(Cargador cargador, int idEstacionCarga) { // Se deberia pasar a que estacion esta asociado
        EstacionCarga estacion = repo.getEstacion(idEstacionCarga);
        if(estacion == null){
            throw new IllegalArgumentException("La estación no existe.");
        }
        cargador.setMiEstacionCarga(estacion);
        repo.guardarCargador(cargador);
    }
    
    @Override
    @Transactional
    public void altaCarga(Carga carga) {
        carga.getCargador().setCargaActiva(carga);
        carga.getMiCliente().setCargaActiva(carga);
        repo.guardarCarga(carga);
    }

    @Override
    @Transactional
    public List<EstacionCargaDTO> obtenerEstaciones() {
        List<EstacionCarga> estaciones = repo.obtenerEstaciones();
        List<EstacionCargaDTO> estacionesDTO = new ArrayList<>();
        for(EstacionCarga ec : estaciones){
            EstacionCargaDTO nuevaEstacion = new EstacionCargaDTO(ec);
            estacionesDTO.add(nuevaEstacion);
        }
        return estacionesDTO;
    }

    @Override
    @Transactional
    public void altaCliente(Cliente cliente) {
        // Se llama solamente cuando el ObserverModuloCliente observa que se creo
        // un nuevo cliente en moduloClientes
        repo.guardarCliente(cliente);
    }

    @Override
    @Transactional
    public void cargaAprovada(boolean aceptado, String cedula) {
        if(!aceptado){
            System.out.println("Pago rechazado.");
            throw new IllegalArgumentException("Pago rechazado.");
        }else{
            Cliente c = repo.getCliente(cedula);
            Carga carga = c.getCargaPendiente();
            c.agregarCargaAHistorial(carga);
            c.setCargaPendiente(null);

            repo.guardarCargaAprobada(c,carga);
        }
    }

    public void altaMedioPago(MedioPago medioPago){
        repo.guardarMedioPago(medioPago);
    }

}
