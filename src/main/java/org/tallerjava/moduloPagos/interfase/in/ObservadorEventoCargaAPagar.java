package org.tallerjava.moduloPagos.interfase.in;

import org.tallerjava.moduloCargas.interfase.evento.out.CargaAPagar;
import org.tallerjava.moduloPagos.aplicacion.ServicioPagos;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class ObservadorEventoCargaAPagar {

    @Inject
    private ServicioPagos servicio;

    public void accept(@Observes CargaAPagar evento) {
        servicio.pagarCarga(evento.getCedula(), Float.valueOf((float) evento.getImporteTotal()), evento.getId());
    }
}
