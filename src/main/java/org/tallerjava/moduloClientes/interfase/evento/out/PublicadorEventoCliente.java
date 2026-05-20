package org.tallerjava.moduloClientes.interfase.evento.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.enterprise.event.Event;
import org.tallerjava.moduloClientes.dominio.Cliente;

@ApplicationScoped
public class PublicadorEventoCliente {

    @Inject
    private Event<ClienteNuevoCliente> nuevoCliente;

    public void publicarNuevoCliente(Cliente cliente){
        ClienteNuevoCliente evento = new ClienteNuevoCliente(
                cliente.getCedula(),
                cliente.getNombreCompleto(),
                cliente.getTelefono(),
                cliente.getContrasenia()
        );

        nuevoCliente.fire(evento);
    }

}
