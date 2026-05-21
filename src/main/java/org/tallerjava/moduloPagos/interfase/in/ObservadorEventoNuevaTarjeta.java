package org.tallerjava.moduloPagos.interfase.in;

import org.tallerjava.moduloClientes.interfase.evento.out.ClienteNuevaTarjeta;
import org.tallerjava.moduloPagos.aplicacion.ServicioPagos;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class ObservadorEventoNuevaTarjeta {

    @Inject
    private ServicioPagos servicio;

    public void accept(@Observes ClienteNuevaTarjeta evento) {
        servicio.guardarTarjeta(Long.valueOf(evento.getId()), evento.getNumero(), evento.getFechaVencimiento(),
                evento.getDigitoVerificador());
    }
}
