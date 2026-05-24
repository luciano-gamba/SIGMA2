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

    public void publicarEventoTarjeta(boolean aprovado, String mensaje, String cedula){
        this.eventoTarjeta.fire(new EventoTarjeta(aprovado, mensaje, cedula));
    }
    public void publicarEventoCuentaUTE(boolean aprovado, String mensaje, String cedula){
        this.eventoCuentaUTE.fire(new EventoCuentaUTE(aprovado, mensaje, cedula));
    }

}
