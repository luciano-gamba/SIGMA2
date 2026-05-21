package org.tallerjava.moduloPagos.interfase.in;

import org.tallerjava.moduloClientes.interfase.evento.out.ClienteNuevoCliente;
import org.tallerjava.moduloPagos.aplicacion.ServicioPagos;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class ObservadorEventoNuevoCliente {

    @Inject
    private ServicioPagos servicio;

    public void accept(@Observes ClienteNuevoCliente evento) {
        servicio.guardarCliente(evento.getCedula(), evento.getNombreCompleto());
    }
}
