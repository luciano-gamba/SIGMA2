package org.tallerjava.moduloClientes.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.tallerjava.moduloClientes.aplicacion.ServicioClientes;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.dominio.repo.ClientesRepositorio;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ServicioClientesImpl implements ServicioClientes {

    // aca iria to do los @Inject que se precisen
    @Inject
    private ClientesRepositorio repo;

    @Override
    public void registrarCliente(Cliente cliente){
        repo.guardarCliente(cliente);
        System.out.println("FUNCIONAAAAAAAAAAAAAAAAAAAAA");
    }

    @Override
    public void altaMedioPago(Cliente cliente, MedioPago medioPago){
        //aca va la implementacion
    }

    @Override
    public List<Cliente> obtenerClientes(){
        List<Cliente> listaClientes = new ArrayList<>();

        //

        return listaClientes;
    }

    @Override
    public void realizarReclamo(Cliente cliente, String comentario){
        //aca va la implementacion
    }

}
