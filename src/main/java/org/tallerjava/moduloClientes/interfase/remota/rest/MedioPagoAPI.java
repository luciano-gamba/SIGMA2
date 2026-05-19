package org.tallerjava.moduloClientes.interfase.remota.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.tallerjava.moduloClientes.aplicacion.ServicioClientes;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;

@ApplicationScoped
@Path("/MedioPago")
public class MedioPagoAPI {

    @Inject
    private ServicioClientes servicioClientes;

    //cliente Comun (cuentaUTE)
    //curl -X POST -v http://localhost:8080/SIGMA2/trafico/MedioPago -H "Content-Type: application/json" -d '{"cliente":{"cedula":"55326750","nombreCompleto":"Alan Nahuel Machado Sosa","telefono":"094755370","contrasenia":"1234"},"medioPago":{"numeroCuenta":"001002003"}}'

    //cliente Profesional (CuentaUTE)
    //curl -X POST -v http://localhost:8080/SIGMA2/trafico/MedioPago -H "Content-Type: application/json" -d '{"cliente":{"cedula":"55326751","nombreCompleto":"Alan Nahuel Machado Sosa","telefono":"094755370","contrasenia":"1234","porcentajeDescuento":"20","tipo":"TAXI"},"medioPago":{"numeroCuenta":"001002003"}}'
    //tendria que devolver "Este cliente no acepta este medio de pago" con un error 500
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void registrarMedioPago(ClienteMedioPagoDTO dto){
        ClientesDTO cliente = dto.getCliente();
        MedioPagoDTO medio = dto.getMedioPago();

        MedioPago medioPago = medio.buildMedioPago();
        Cliente cli = cliente.buildCliente();
        servicioClientes.altaMedioPago(cli, medioPago);
    }

}
