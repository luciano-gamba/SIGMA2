package org.tallerjava.moduloMonitoreo.interfase.evento.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerjava.moduloCargas.interfase.evento.out.CargaAPagar;
import org.tallerjava.moduloCargas.interfase.evento.out.EventoCargaActiva;
import org.tallerjava.moduloMonitoreo.infraestructura.RegistradorDeMetricas;

@ApplicationScoped
public class ObserverModuloCargas {
    private static final Logger log = Logger.getLogger(ObserverModuloCargas.class);

    @Inject
    private RegistradorDeMetricas register;

    public void accept(@Observes CargaAPagar event) {
        if(event.isFinalizoCarga()){
            log.infof("Carga procesada: %s", event.toString());
            register.incrementarCounter(RegistradorDeMetricas.b_cantidad_de_cargas_realizadas);
        }
    }

    public void accept(@Observes EventoCargaActiva event){
        log.infof("Carga procesada: %s", event.toString());
        register.incrementarCounter(RegistradorDeMetricas.a_cantidad_de_cargas_activas);
    }
}
