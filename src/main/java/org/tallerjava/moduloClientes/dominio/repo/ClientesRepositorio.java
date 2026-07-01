package org.tallerjava.moduloClientes.dominio.repo;

import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.dominio.Reclamo;

import java.util.List;

public interface ClientesRepositorio {

    void guardarCliente(Cliente cliente);

    List<Cliente> obtenerClientes();

    Cliente getCliente(String ci, String contrasenia);

    void altaMedioPago(Cliente cliente, MedioPago medioPago);

    Cliente getClienteSC(String ci);

    void guardarReclamo(Reclamo reclamo);

    List<MedioPago> getMediosPago();
}
