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

        if(c.getCargaPendiente()!=null){
            //si tiene alguna carga pendiente de pagar, no le permite iniciar una carga nueva hasta que la pague
            return;
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
        return c.getCargaActiva().getPorcentajeAvance();
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

        Carga c = cargador.getCargaActiva();
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
        Carga c = miCliente.getCargaPendiente();
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
        System.out.println("ID ESTACION CARGA DESDE ALTA CARGADOR: " + idEstacionCarga);
        EstacionCarga estacion = repo.getEstacion(idEstacionCarga);
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
            return;
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
