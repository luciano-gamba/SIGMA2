package org.tallerjava.moduloClientes.aplicacion;

import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;

import java.util.List;

public interface ServicioClientes {

    public void registrarCliente(Cliente cliente);
    // entiendo que dataos cliente debe de ser un DataType)?
    // o sino simplemente son todos los datos de cliente asi nomas.
    // la otra opcion es pasarle cliente y fue
    // supongo que habria que tener en cuenta si e un usuario comun o profesional

    public void altaMedioPago(Cliente cliente, MedioPago medioPago);

    public List<Cliente> obtenerClientes();
    //devuelve los clientes del sistema.
    //le puse list pero no se si terminaremos usando eso
    // aunque supongo que si

    public void realizarReclamo(Cliente cliente, String comentario);
    //esto no tiene sentido. En la letra dice:

    /*
    Permite realizar reclamos a los usuarios registrando
    el comentario realizado.

    pero como puede ser que marquemos el reclamo con el comentario
    si no le pasamos parametros a esta funcion?

    Entiendo que debe de ser con el uso de eventos
    ahora mismo no sabria como hacer que esto funcione como dice la
    letra siendo que no le pasamos los datos.
    */

}
