package org.tallerjava.moduloCargas.interfase.evento.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class PublicadorEventoCarga {

    @Inject
    private Event<CargaAPagar> nuevoPago;

    @Inject
    private Event<EventoCargaActiva> cargaCambiada;

    public void publicarNuevoPagoCarga(String cedula, double importeTotal, long idPago, boolean finalizoCarga){
        CargaAPagar evento = new CargaAPagar(cedula, importeTotal, idPago, finalizoCarga);
        nuevoPago.fire(evento);
    }

    public void publicarCambioCarga(boolean activo){
        EventoCargaActiva evento = new EventoCargaActiva(activo);

        cargaCambiada.fire(evento);
    }
}
