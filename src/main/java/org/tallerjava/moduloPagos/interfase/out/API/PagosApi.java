package org.tallerjava.moduloPagos.interfase.out.API;

import java.util.List;

import org.tallerjava.moduloPagos.aplicacion.ServicioPagos;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/moduloPagos")
public class PagosApi {

    @Inject
    private ServicioPagos servicioPagos;

    @GET
    @Path("/pagos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PagoDTO> getPagos(HistoricoDTO historicoDTO){
        return servicioPagos.consultarPagos(historicoDTO.getCedula(), historicoDTO.getInicio(),historicoDTO.getFin());
    }
}