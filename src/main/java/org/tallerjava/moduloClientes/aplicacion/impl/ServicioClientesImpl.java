package org.tallerjava.moduloClientes.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.tallerjava.moduloClientes.aplicacion.ServicioClientes;
import org.tallerjava.moduloClientes.dominio.*;
import org.tallerjava.moduloClientes.dominio.repo.ClientesRepositorio;
import org.tallerjava.moduloClientes.interfase.evento.out.PublicadorEventoCliente;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ServicioClientesImpl implements ServicioClientes {

    // aca iria to do los @Inject que se precisen
    @Inject
    private ClientesRepositorio repo;

    @Inject
    private PublicadorEventoCliente evento;

    @Override
    @Transactional
    public void registrarCliente(Cliente cliente){
        repo.guardarCliente(cliente);
        evento.publicarNuevoCliente(cliente);
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
    public void altaMedioPago(Cliente cliente, MedioPago medioPago){

        if ((medioPago instanceof CuentaUTE) && (cliente instanceof Profesional)){
            throw new IllegalArgumentException(
                    "Este cliente no acepta este medio de pago"
            );
        }else{
            cliente.getMediosDePago().add(medioPago);
            repo.altaMedioPago(cliente, medioPago);
        }

    }

    @Override
    public List<Cliente> obtenerClientes(){
        List<Cliente> listaClientes;

        listaClientes = repo.obtenerClientes();

        return listaClientes;
    }

    @Override
    public void realizarReclamo(Cliente cliente, String comentario){
        //aca va la implementacion
    }

}
