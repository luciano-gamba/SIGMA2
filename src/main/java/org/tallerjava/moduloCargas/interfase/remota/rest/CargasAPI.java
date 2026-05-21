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

    //curl -X POST -v http://localhost:8080/SIGMA2/moduloCarga/ -H "Content-Type: application/json" -d '{}'
    @POST
    @Path("/carga/iniciar")
    @Consumes(MediaType.APPLICATION_JSON)
    public void iniciarCarga(CargaIniciarDTO dto){
        Cargador cargador = repo.getCargador(dto.getIdCargador());
        Cliente c = repo.getCliente(dto.getCedula());
        long pago = dto.getIdMedioPago();
        servicioCarga.iniciarCarga(cargador,c,pago);
    }

    @GET
    @Path("/get/cargaActual")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int verCargaActual(String cedula){
        Cliente c = repo.getCliente(cedula);
        return servicioCarga.verCargaActual(c);
    }

    @GET
    @Path("/get/historico")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Carga> verHistorico(HistoricoDTO dto){
        Cliente c = repo.getCliente(dto.getCedula());
        LocalDateTime inicio = dto.getInicio();
        LocalDateTime fin = dto.getFin();
        return servicioCarga.verHistorico(c, inicio, fin);
    }

//    curl -v http://localhost:8080/SIGMA2/moduloCargas/get/estaciones
    @GET
    @Path("/get/estaciones")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstacionCarga> obtenerEstaciones(){
        return servicioCarga.obtenerEstaciones();
    }

    @POST
    @Path("/carga/finalizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public void finalizarCarga(CargaFinalizarDTO dto){
        Cargador car = repo.getCargador(dto.getIdCargador());
        servicioCarga.finalizarCarga(car, dto.getTiempoRecargo());
    }

//    curl -X POST -v http://localhost:8080/SIGMA2/moduloCargas/alta/estacion -H "Content-Type: application/json" -d '{"descripcion":"Estacion de carga de San Luis","calle":"artigas esquina 12","departamento":"Canelones","longitud":"1947","latitud":"9284"}'
    @POST
    @Path("/alta/estacion")
    @Consumes(MediaType.APPLICATION_JSON)
    public void altaEstacion(EstacionCarga estacion){ //id, desc, calle, dep, longitud, latitud
        servicioCarga.altaEstacion(estacion);
    }

    @POST
    @Path("/alta/cargador")
    @Consumes(MediaType.APPLICATION_JSON)
    public void altaCargador(Cargador c, int idEstacionCarga){
        EstacionCarga estacion = repo.getEstacion(idEstacionCarga);
        c.setMiEstacionCarga(estacion);
        servicioCarga.altaCargador(c);
    }
}
