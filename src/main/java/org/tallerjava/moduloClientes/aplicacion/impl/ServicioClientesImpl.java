package org.tallerjava.moduloClientes.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.tallerjava.moduloClientes.aplicacion.ServicioClientes;
import org.tallerjava.moduloClientes.dominio.*;
import org.tallerjava.moduloClientes.dominio.repo.ClientesRepositorio;
import org.tallerjava.moduloClientes.infraestructura.messaging.EnviarReclamoUtil;
import org.tallerjava.moduloClientes.interfase.evento.out.PublicadorEventoCliente;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ServicioClientesImpl implements ServicioClientes {

    @Inject
    private ClientesRepositorio repo;

    @Inject
    private PublicadorEventoCliente evento;

    @Inject
    private EnviarReclamoUtil mensajeReclamo;

    @Override
    @Transactional
    public Response registrarCliente(Cliente cliente){
        repo.guardarCliente(cliente);
        if (cliente instanceof ClienteComun){
            evento.publicarNuevoCliente(cliente, 0);
        }else{
            Profesional clientePro = (Profesional)cliente;
            evento.publicarNuevoCliente(cliente, clientePro.getPorcentajeDescuento());
        }

        return Response.status(Response.Status.OK)
                .entity("{\"Usuario creado correctamente.\"}")
                .build();
    }

    @Override
    @Transactional
    public Response iniciarSesion(String ci, String contrasenia){
        Cliente c = repo.getCliente(ci, contrasenia);
        if (c == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"Usuario no encontrado, o cedula o contraseña incorrecta.\"}")
                    .build();
        }else {
            return Response.status(Response.Status.OK)
                    .entity(c)
                    .build();
        }

    }

    @Override
    @Transactional
    public Response altaMedioPago(String ci, MedioPago medioPago){

        for (MedioPago mp : repo.getMediosPago()){
            if ((medioPago instanceof CuentaUTE) && (mp instanceof CuentaUTE)){
                if(((CuentaUTE) medioPago).getNumeroCuenta().equals(((CuentaUTE) mp).getNumeroCuenta())){
                    return Response.status(Response.Status.CONFLICT)
                            .entity("{\"Este medio de pago ya esta añadido a otro cliente\"}")
                            .build();
                }
            }
            if ((medioPago instanceof ClienteTarjeta) && (mp instanceof ClienteTarjeta)){
                if(((ClienteTarjeta) medioPago).getNumero().equals(((ClienteTarjeta) mp).getNumero())){
                    return Response.status(Response.Status.CONFLICT)
                            .entity("{\"Este medio de pago ya esta añadido a otro cliente\"}")
                            .build();
                }
            }
        }


        Cliente cliente = repo.getClienteSC(ci);
        if ((medioPago instanceof CuentaUTE) && (cliente instanceof Profesional)){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"Este cliente no acepta este medio de pago.\"}")
                    .build();
        }else{
            cliente.getMediosDePago().add(medioPago);
            repo.altaMedioPago(cliente, medioPago);
            if (medioPago instanceof ClienteTarjeta){
                evento.publicarNuevaTarjeta((ClienteTarjeta)medioPago);
            }else {
                evento.publicarNuevaCuentaUTE((CuentaUTE)medioPago);
            }
            return Response.status(Response.Status.OK)
                    .entity("{\"Medio de pago agregado correctamente.\"}")
                    .build();
        }

    }

    @Override
    public Response obtenerClientes(){
        List<Cliente> listaClientes;

        listaClientes = repo.obtenerClientes();

        return Response.status(Response.Status.OK)
                .entity(listaClientes)
                .build();
//        return listaClientes;
    }

    @Override
    @Transactional
    public Response realizarReclamo(String ci, String comentario){
        Cliente cliente = repo.getClienteSC(ci);

        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"Usuario no encontrado.\"}")
                    .build();
        }

        ReclamoRealizadoMessage reclamo = new ReclamoRealizadoMessage(ci, comentario, LocalDate.now());
        mensajeReclamo.enviarMensaje(reclamo.toJson());

        return Response.status(Response.Status.OK)
                .entity("{\"Gracias por su reclamo. Lo tomaremos en cuenta.\"}")
                .build();
    }

    @Override
    @Transactional
    public void guardarReclamo(ReclamoRealizadoMessage reclamo, String clasificacion){
        evento.publicarNuevoReclamo(clasificacion);
        repo.guardarReclamo(new Reclamo(reclamo, clasificacion));
    }

}
