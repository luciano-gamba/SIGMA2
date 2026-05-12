package org.tallerjava.moduloClientes.aplicacion.Impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.tallerjava.moduloClientes.aplicacion.ServicioClientes;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ServicioClientesImpl implements ServicioClientes {

    // aca iria to do los @Inject que se precisen


    @Override
    public void registrarCliente(Cliente cliente){
        //aca va la implementacion
    }

    @Override
    public void altaMedioPago(Cliente cliente, MedioPago medioPago){
        //aca va la implementacion
    }

    @Override
    public List<Cliente> obtenerClientes(){
        List<Cliente> listaClientes = new ArrayList<>();

        //aca se rellenaria la lista

        return listaClientes;
    }

    @Override
    public void realizarReclamo(Cliente cliente, String comentario){
        //aca va la implementacion
    }

}
