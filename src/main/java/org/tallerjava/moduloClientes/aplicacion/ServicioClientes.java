package org.tallerjava.moduloClientes.aplicacion;

import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.dominio.ReclamoRealizadoMessage;

import java.util.List;

public interface ServicioClientes {

    void registrarCliente(Cliente cliente);

    Cliente iniciarSesion(String ci, String contrasenia);

    void altaMedioPago(String ci, MedioPago medioPago);

    List<Cliente> obtenerClientes();

    void realizarReclamo(String ci, String comentario);

    void guardarReclamo(ReclamoRealizadoMessage reclamo, String clasificacion);

}
