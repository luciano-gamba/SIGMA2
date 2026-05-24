package org.tallerjava.moduloCargas.interfase.remota.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloCargas.aplicacion.ServicioCarga;

import jakarta.inject.Inject;
import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.dto.EstacionCargaDTO;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Path("/moduloCargas")
public class CargasAPI {

    @Inject
    ServicioCarga servicioCarga;

    //curl -X POST -v http://localhost:8080/SIGMA2/moduloCargas/carga/iniciar -H "Content-Type: application/json" -d '{"idCargador": 1,"cedula":"55326750","idMedioPago":1}'
    @POST
    @Path("/carga/iniciar")
    @Consumes(MediaType.APPLICATION_JSON)
    public void iniciarCarga(CargaIniciarDTO dto){
        int cargador = dto.getIdCargador();
        String c = dto.getCedula();
        long pago = dto.getIdMedioPago();
        servicioCarga.iniciarCarga(cargador,c,pago);
    }

    // @POST
    // @Path("/set/cargaActual/{id}")
    // @Consumes(MediaType.APPLICATION_JSON)
    // public void setPorcentajeCarga(@PathParam("id") String cedula, int
    // porcentaje){ //funcionará con dos parametros????
    // servicioCarga.setPorcentajeCarga(cedula, porcentaje);
    // }

    // curl -X GET -v http://localhost:8080/SIGMA2/moduloCargas/get/cargaActual/59924162
    @GET
    @Path("/get/cargaActual/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public int verCargaActual(@PathParam("id") String cedula){
        return servicioCarga.verCargaActual(cedula);
    }

    @GET
    @Path("/get/historico")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Carga> verHistorico(HistoricoDTO dto){
        String cedula = dto.getCedula();
        LocalDateTime inicio = dto.getInicio();
        LocalDateTime fin = dto.getFin();
        return servicioCarga.verHistorico(cedula, inicio, fin);
    }

//    curl -v http://localhost:8080/SIGMA2/moduloCargas/get/estaciones
    @GET
    @Path("/get/estaciones")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstacionCargaDTO> obtenerEstaciones(){
        return servicioCarga.obtenerEstaciones();
    }

//    curl -X POST -v http://localhost:8080/SIGMA2/moduloCargas/carga/finalizar -H "Content-Type: application/json" -d '{"idCargador":1,"tiempoRecargo":0}'
    @POST
    @Path("/carga/finalizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public void finalizarCarga(CargaFinalizarDTO dto){
        int idCar = dto.getIdCargador();
        servicioCarga.finalizarCarga(idCar, dto.getTiempoRecargo());
    }

//    curl -X POST -v http://localhost:8080/SIGMA2/moduloCargas/alta/estacion -H "Content-Type: application/json" -d '{"descripcion":"Estacion de carga de San Luis","calle":"artigas esquina 12","departamento":"Canelones","longitud":"1947","latitud":"9284"}'
    @POST
    @Path("/alta/estacion")
    @Consumes(MediaType.APPLICATION_JSON)
    public void altaEstacion(EstacionCarga estacion){ //id, desc, calle, dep, longitud, latitud
        servicioCarga.altaEstacion(estacion);
    }

//  curl -X POST -v http://localhost:8080/SIGMA2/moduloCargas/alta/cargador -H "Content-Type: application/json" -d '{"tipoCargador": 2,"tieneCable": true,"tipoConector": 2,"costePorHora": 3,"miEstacionCarga": 1}'
    @POST
    @Path("/alta/cargador")
    @Consumes(MediaType.APPLICATION_JSON)
    public void altaCargador(CargadorDTO dto){
        System.out.println("ID ESTACION CARGA: " + dto.getMiEstacionCarga());
        Cargador c = dto.buildCargador();
        int idEstacionCarga = dto.getMiEstacionCarga();
        servicioCarga.altaCargador(c, idEstacionCarga);
    }

    // curl -X POST -v http://localhost:8080/SIGMA2/moduloCargas/carga/reintentar/59924162
    @POST
    @Path("/carga/reintentar/{id}")
    public void reintentarPago(@PathParam("id") String cedula){
        servicioCarga.reintentarPago(cedula);
    }
}
