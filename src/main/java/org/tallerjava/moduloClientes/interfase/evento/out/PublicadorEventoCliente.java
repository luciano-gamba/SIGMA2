package org.tallerjava.moduloClientes.interfase.evento.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.enterprise.event.Event;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.ClienteTarjeta;
import org.tallerjava.moduloClientes.dominio.CuentaUTE;

@ApplicationScoped
public class PublicadorEventoCliente {

    @Inject
    private Event<ClienteNuevoCliente> nuevoCliente;

    @Inject
    private Event<ClienteNuevaTarjeta> nuevaTarjeta;

    @Inject
    private Event<ClienteNuevaCuentaUTE> nuevaCuentaUTE;

    public void publicarNuevoCliente(Cliente cliente, double descuento){
        ClienteNuevoCliente evento = new ClienteNuevoCliente(
                cliente.getCedula(),
                cliente.getNombreCompleto(),
                cliente.getTelefono(),
                cliente.getContrasenia(),
                descuento
        );

        nuevoCliente.fire(evento);
    }

    public void publicarNuevaTarjeta(ClienteTarjeta tarjeta){
        ClienteNuevaTarjeta evento = new ClienteNuevaTarjeta(
                tarjeta.getId(),
                tarjeta.getNumero(),
                tarjeta.getFechaVencimiento(),
                tarjeta.getDigitoVerificador()
        );

        nuevaTarjeta.fire(evento);
    }

    public void publicarNuevaCuentaUTE(CuentaUTE cuenta){
        ClienteNuevaCuentaUTE evento = new ClienteNuevaCuentaUTE(
                cuenta.getId(),
                cuenta.getNumeroCuenta()
        );

        nuevaCuentaUTE.fire(evento);
    }

}
