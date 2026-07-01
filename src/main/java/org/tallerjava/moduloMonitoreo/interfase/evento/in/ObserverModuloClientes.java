package org.tallerjava.moduloMonitoreo.interfase.evento.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerjava.moduloClientes.interfase.evento.out.ClienteNuevoReclamo;
import org.tallerjava.moduloMonitoreo.infraestructura.RegistradorDeMetricas;

@ApplicationScoped
public class ObserverModuloClientes {
    @Inject
    private RegistradorDeMetricas register;

    public void accept(@Observes ClienteNuevoReclamo event) {
        switch(event.getClasificacion()){
            case "URGENTE":
                register.incrementarCounter(RegistradorDeMetricas.f_reclamo_urgente);
                break;
            case "NORMAL":
                register.incrementarCounter(RegistradorDeMetricas.g_reclamo_normal);
                break;
            case "BAJA":
                register.incrementarCounter(RegistradorDeMetricas.h_reclamo_baja);
                break;
            default:
                System.out.println("ERROR");
        }
    }

}
