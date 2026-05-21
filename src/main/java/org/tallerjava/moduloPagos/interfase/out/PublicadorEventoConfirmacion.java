package org.tallerjava.moduloPagos.interfase.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class PublicadorEventoConfirmacion {
    
    @Inject
    private Event<EventoTarjeta> eventoTarjeta;

    @Inject
    private Event<EventoCuentaUTE> eventoCuentaUTE;

    public void publicarEventoTarjeta(boolean aprovado, String mensaje){
        this.eventoTarjeta.fire(new EventoTarjeta(aprovado, mensaje));
    }
    public void publicarEventoCuentaUTE(boolean aprovado, String mensaje){
        this.eventoCuentaUTE.fire(new EventoCuentaUTE(aprovado, mensaje));
    }

}
