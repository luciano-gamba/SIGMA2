package org.tallerjava.moduloClientes.interfase.remota.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.tallerjava.moduloClientes.aplicacion.ServicioClientes;
import org.tallerjava.moduloClientes.dominio.Cliente;

@ApplicationScoped
@Path("/moduloCliente")
public class ClientesAPI {

    @Inject
    private ServicioClientes servicioClientes;

    //Cliente comun
    //curl -X POST -v http://localhost:8080/SIGMA2/moduloCliente -H "Content-Type: application/json" -d '{"cedula":"55326750","nombreCompleto":"Alan Nahuel Machado Sosa","telefono":"094755370","contrasenia":"1234","mediosDePago":[]}'

    //Cliente profesional (TAXI, 20% de descuento)
    //curl -X POST -v http://localhost:8080/SIGMA2/moduloCliente -H "Content-Type: application/json" -d '{"cedula":"55326751","nombreCompleto":"Alan Nahuel Machado Sosa","telefono":"094755370","contrasenia":"1234","porcentajeDescuento":"20","tipo":"TAXI"}'
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarCliente(ClientesDTO clienteDTO){
        Cliente cli = clienteDTO.buildCliente();
        return servicioClientes.registrarCliente(cli);
    }

    //curl http://localhost:8080/SIGMA2/moduloCliente/getClientes
    @GET
    @Path("/getClientes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientes(){
        return servicioClientes.obtenerClientes();
    }

    //curl -X POST -v http://localhost:8080/SIGMA2/moduloCliente/iniciarSesion -H "Content-Type: application/json" -d '{"cedula":"55326750","contrasenia":"1234"}'
    @POST
    @Path("/iniciarSesion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarSesion(ClienteSesionDTO clienteSesionDTO){
        Cliente cli = clienteSesionDTO.buildCliente();
        if (cli == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"Error en el request, Revisa el request e intentelo nuevamente.\"}")
                    .build();
        }else{
            return servicioClientes.iniciarSesion(cli.getCedula(), cli.getContrasenia());
        }

    }

    //curl -X POST -v http://localhost:8080/SIGMA2/moduloCliente/realizarReclamo -H "Content-Type: application/json" -d '{"cedula":"55326750", "comentario":"NO FUNCIONA EL CARGADOR!"}'
    @POST
    @Path("/realizarReclamo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarReclamo(ReclamoDTO reclamoDTO){
        String ci = reclamoDTO.getCedula();
        String comentario = reclamoDTO.getComentario();

        return servicioClientes.realizarReclamo(ci, comentario);
    }

}
