package org.tallerjava.moduloPagos.interfase.in;

import org.tallerjava.moduloClientes.interfase.evento.out.ClienteNuevaCuentaUTE;
import org.tallerjava.moduloPagos.aplicacion.ServicioPagos;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class ObservadorEventoNuevaCuentaUTE {

    @Inject
    private ServicioPagos servicio;

    public void accept(@Observes ClienteNuevaCuentaUTE evento) {
        servicio.guardarCuentaUTE(Long.valueOf(evento.getId()), evento.getNumeroCuenta());
    }
}
