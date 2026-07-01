package org.tallerjava.moduloCargas.interfase.evento.in;

import org.tallerjava.moduloCargas.aplicacion.ServicioCarga;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerjava.moduloPagos.interfase.out.EventoCuentaUTE;
import org.tallerjava.moduloPagos.interfase.out.EventoTarjeta;

@ApplicationScoped
public class ObserverModuloPagos {
    @Inject
    private ServicioCarga servicioCarga;

    public void accept(@Observes EventoTarjeta event){
        servicioCarga.cargaAprobada(event.isAprobado(), event.getCedula());
    }

    public void accept(@Observes EventoCuentaUTE event){
        servicioCarga.cargaAprobada(event.isAprobado(), event.getCedula());
    }
}
