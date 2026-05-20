package org.tallerjava.moduloCargas.interfase.evento.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class PublicadorEventoCarga {

    @Inject
    private Event<CargaAPagar> nuevoPago;

    public void publicarNuevoPagoCarga(String cedula, double importeTotal, long idPago){
        CargaAPagar evento = new CargaAPagar(cedula, importeTotal, idPago);

        nuevoPago.fire(evento);
    }
}
