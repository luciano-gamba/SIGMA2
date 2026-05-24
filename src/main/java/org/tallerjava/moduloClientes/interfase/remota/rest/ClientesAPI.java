package org.tallerjava.moduloClientes.interfase.remota.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import org.tallerjava.moduloClientes.aplicacion.ServicioClientes;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.Reclamo;

import java.util.List;

@ApplicationScoped
@Path("/moduloCliente")
public class ClientesAPI {
    private static final Logger log = Logger.getLogger(ClientesAPI.class);

    @Inject
    private ServicioClientes servicioClientes;

    //Cliente comun
    //curl -X POST -v http://localhost:8080/SIGMA2/moduloCliente -H "Content-Type: application/json" -d '{"cedula":"55326750","nombreCompleto":"Alan Nahuel Machado Sosa","telefono":"094755370","contrasenia":"1234","mediosDePago":[]}'

    //Cliente profesional (TAXI, 20% de descuento)
    //curl -X POST -v http://localhost:8080/SIGMA2/moduloCliente -H "Content-Type: application/json" -d '{"cedula":"55326751","nombreCompleto":"Alan Nahuel Machado Sosa","telefono":"094755370","contrasenia":"1234","porcentajeDescuento":"20","tipo":"TAXI"}'
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void registrarCliente(ClientesDTO clienteDTO){
        log.infof("Nuevo usuario: %s", clienteDTO);

        Cliente cli = clienteDTO.buildCliente();
        servicioClientes.registrarCliente(cli);
    }

    //curl http://localhost:8080/SIGMA2/moduloCliente/getClientes
    @GET
    @Path("/getClientes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> getClientes(){
        return servicioClientes.obtenerClientes();
    }

    //curl -X POST -v http://localhost:8080/SIGMA2/moduloCliente/iniciarSesion -H "Content-Type: application/json" -d '{"cedula":"55326750","contrasenia":"1234"}'
    @POST
    @Path("/iniciarSesion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Cliente iniciarSesion(ClienteSesionDTO clienteSesionDTO){
        Cliente cli = clienteSesionDTO.buildCliente();
        if (cli == null) {
            return null;
        }else{
            return servicioClientes.iniciarSesion(cli.getCedula(), cli.getContrasenia());
        }

    }

    //curl -X POST -v http://localhost:8080/SIGMA2/moduloCliente/realizarReclamo -H "Content-Type: application/json" -d '{"cliente":{"cedula":"55326750"}, "comentario":"NO FUNCIONA EL CARGADOR!"}'
    @POST
    @Path("/realizarReclamo")
    @Consumes(MediaType.APPLICATION_JSON)
    public void registrarReclamo(ReclamoDTO reclamoDTO){
        ClienteCiDTO cliente = reclamoDTO.getCliente();
        String comentario = reclamoDTO.getComentario();

        String ci = cliente.getCedula();
        servicioClientes.realizarReclamo(ci, comentario);
    }

}
