package org.tallerjava.moduloPagos.interfase.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class PublicadorEventoPago {
    
    @Inject
    private Event<EventoTarjeta> eventoTarjeta;

    @Inject
    private Event<EventoCuentaUTE> eventoCuentaUTE;

    public void publicarEventoTarjeta(boolean aprobado, String mensaje, String cedula){
        this.eventoTarjeta.fire(new EventoTarjeta(aprobado, mensaje, cedula));
    }
    public void publicarEventoCuentaUTE(boolean aprobado, String mensaje, String cedula){
        this.eventoCuentaUTE.fire(new EventoCuentaUTE(aprobado, mensaje, cedula));
    }

}
