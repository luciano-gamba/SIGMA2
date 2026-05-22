package org.tallerjava.moduloCargas.interfase.evento.in;

//import java.util.logging.Logger;

import org.tallerjava.moduloCargas.aplicacion.ServicioCarga;
import org.tallerjava.moduloCargas.dominio.Cliente;
import org.tallerjava.moduloClientes.interfase.evento.out.ClienteNuevoCliente;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerjava.moduloPagos.interfase.out.EventoTarjeta;

@ApplicationScoped
public class ObserverModuloPagos {
    //private static final Logger log = Logger.getLogger(ObserverModuloCliente.class);

    @Inject
    private ServicioCarga servicioCarga;

    public void accept(@Observes EventoTarjeta event){
        servicioCarga.cargaAprovada(event.isAprovado(), event.getCedula());
    }
}
