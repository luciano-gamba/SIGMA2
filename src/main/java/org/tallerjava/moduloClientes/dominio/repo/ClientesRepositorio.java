package org.tallerjava.moduloClientes.dominio.repo;

import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;

import java.util.List;

public interface ClientesRepositorio {

    public void guardarCliente(Cliente cliente);

    public List<Cliente> obtenerClientes();

    public Cliente getCliente(String ci, String contrasenia);

    public void altaMedioPago(Cliente cliente, MedioPago medioPago);
}
