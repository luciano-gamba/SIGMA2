package org.tallerjava.moduloCargas.interfase.remota.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloCargas.aplicacion.ServicioCarga;

import jakarta.inject.Inject;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.repo.CargasRepositorio;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Path("/moduloCargas")
public class CargasAPI {

    @Inject
    ServicioCarga servicioCarga;

    @Inject
    private CargasRepositorio repo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void iniciarCarga(CargaIniciarDTO dto){
        Cargador cargador = repo.getCargador(dto.getIdCargador());
        Cliente c = repo.getCliente(dto.getCedula());
//        MedioPago pago = repo.getMedioPago(dto.getIdMedioPago());
//        servicioCarga.iniciarCarga(cargador,c,pago);
        // hasta que no se haga la integracion de MedioPago a la BD y a este modulo no puede andar este endpoint
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int verCargaActual(String cedula){
        Cliente c = repo.getCliente(cedula);
        return servicioCarga.verCargaActual(c);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Carga> verHistorico(HistoricoDTO dto){
        Cliente c = repo.getCliente(dto.getCedula());
        LocalDateTime inicio = dto.getInicio();
        LocalDateTime fin = dto.getFin();
        return servicioCarga.verHistorico(c, inicio, fin);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void finalizarCarga(CargaFinalizarDTO dto){
//        Carga car = repo.getCarga(); // como encuentro la carga??
//        servicioCarga.finalizarCarga(car, dto.getTiempoRecargo());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void altaEstacion(EstacionCarga estacion){ //id, desc, calle, dep, longitud, latitud
        servicioCarga.altaEstacion(estacion);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void altaCargador(Cargador c){

    }
}
