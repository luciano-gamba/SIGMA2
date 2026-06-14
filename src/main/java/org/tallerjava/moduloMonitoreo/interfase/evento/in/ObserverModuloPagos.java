package org.tallerjava.moduloMonitoreo.interfase.evento.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerjava.moduloMonitoreo.infraestructura.RegistradorDeMetricas;
import org.tallerjava.moduloPagos.interfase.out.EventoCuentaUTE;
import org.tallerjava.moduloPagos.interfase.out.EventoTarjeta;

/**
 * Observese que este Modulo si esta acoplado con el módulo de Gestion (ya que conoce sus eventos)
 * Idem para cada modulo que lanza eventos
 *
 * Un nivel mayor de desacoplamiento lo podemos lograr con JMS
 *
 */
@ApplicationScoped
public class ObserverModuloPagos {
    private static final Logger log = Logger.getLogger(ObserverModuloPagos.class);

    @Inject
    private RegistradorDeMetricas register;

    public void accept(@Observes EventoCuentaUTE event) {
        log.infof("Evento procesado: GestionPagoCuentaPrePaga: %s", event.getMensaje());
        register.incrementarCounter(RegistradorDeMetricas.c_cantidad_de_pagos_realizados_con_UTE);
    }
    public void accept(@Observes EventoTarjeta event) {
        log.infof("Evento procesado: GestionPagoCuentaPostPaga: %s", event.getMensaje());
        if (event.isAprobado()) {
           register.incrementarCounter(RegistradorDeMetricas.d_cantidad_de_pagos_realizados_con_Tarjetas);            
        } else {
            register.incrementarCounter(RegistradorDeMetricas.e_ocurrió_un_error_al_pagar_con_Tarjeta);
        }
    }
}
