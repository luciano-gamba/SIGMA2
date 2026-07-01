package org.tallerjava.moduloClientes.aplicacion;

import jakarta.ws.rs.core.Response;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.dominio.ReclamoRealizadoMessage;

import java.util.List;

public interface ServicioClientes {

    Response registrarCliente(Cliente cliente);

    Response iniciarSesion(String ci, String contrasenia);

    Response altaMedioPago(String ci, MedioPago medioPago);

    Response obtenerClientes();

    Response realizarReclamo(String ci, String comentario);

    void guardarReclamo(ReclamoRealizadoMessage reclamo, String clasificacion);

}
