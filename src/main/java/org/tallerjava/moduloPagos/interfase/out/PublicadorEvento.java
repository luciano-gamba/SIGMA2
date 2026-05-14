package org.tallerjava.moduloPagos.interfase.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class PublicadorEvento {
    
    @Inject
    private Event<EventoTarjeta> eventoTarjeta;

    @Inject
    private Event<EventoCuentaUTE> eventoCuentaUTE;

    /*
    public void publicarVehiculoNoEncontrado(String mensaje){
        vehiculoNoEncontrado.fire(new PeajeVehiculoNoEncontrado(mensaje));
    }
    public void publicarPagoSucive(String mensaje){
        pagoSuciveEvento.fire(new PeajePagoSucive(mensaje));
    }

    public void publicarEventoTarjeta(){
        this.eventoTarjeta.fire(eventoTarjeta);
    }
    public void publicarEventoCuentaUTE(CuentaUTE){
        this.eventoCuentaUTE.fire(new EventoCuentaUTE());
    }
    */
}
