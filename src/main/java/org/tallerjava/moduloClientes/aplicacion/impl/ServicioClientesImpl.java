package org.tallerjava.moduloClientes.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloClientes.aplicacion.ServicioClientes;
import org.tallerjava.moduloClientes.dominio.*;
import org.tallerjava.moduloClientes.dominio.repo.ClientesRepositorio;
import org.tallerjava.moduloClientes.interfase.evento.out.PublicadorEventoCliente;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ServicioClientesImpl implements ServicioClientes {

    @Inject
    private ClientesRepositorio repo;

    @Inject
    private PublicadorEventoCliente evento;

    @Override
    @Transactional
    public void registrarCliente(Cliente cliente){
        repo.guardarCliente(cliente);
        if (cliente instanceof ClienteComun){
            evento.publicarNuevoCliente(cliente, 0);
        }else{
            Profesional clientePro = (Profesional)cliente;
            evento.publicarNuevoCliente(cliente, clientePro.getPorcentajeDescuento());
        }

        System.out.println("Guardando Cliente...");
    }

    @Override
    @Transactional
    public Cliente iniciarSesion(String ci, String contrasenia){
        Cliente c = repo.getCliente(ci, contrasenia);
        if (c == null) {
            return null;
        }else {
            return c;
        }

    }

    @Override
    @Transactional
    public void altaMedioPago(String ci, MedioPago medioPago){
        Cliente cliente = repo.getClienteSC(ci);
        if ((medioPago instanceof CuentaUTE) && (cliente instanceof Profesional)){
            throw new IllegalArgumentException(
                    "Este cliente no acepta este medio de pago"
            );
        }else{
            cliente.getMediosDePago().add(medioPago);
            repo.altaMedioPago(cliente, medioPago);
            if (medioPago instanceof ClienteTarjeta){
                evento.publicarNuevaTarjeta((ClienteTarjeta)medioPago);
            }else {
                evento.publicarNuevaCuentaUTE((CuentaUTE)medioPago);
            }
        }

    }

    @Override
    public List<Cliente> obtenerClientes(){
        List<Cliente> listaClientes;

        listaClientes = repo.obtenerClientes();

        return listaClientes;
    }

    @Override
    @Transactional
    public void realizarReclamo(String ci, String comentario){
        Reclamo reclamo = new Reclamo(ci, comentario, LocalDate.now());

        repo.guardarReclamo(reclamo);
    }

}
