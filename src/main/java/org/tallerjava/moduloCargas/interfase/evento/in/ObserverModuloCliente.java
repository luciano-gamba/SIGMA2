package org.tallerjava.moduloCargas.interfase.evento.in;

import org.tallerjava.moduloCargas.aplicacion.ServicioCarga;
import org.tallerjava.moduloCargas.dominio.Cliente;
import org.tallerjava.moduloCargas.dominio.MedioPago;
import org.tallerjava.moduloClientes.interfase.evento.out.ClienteNuevaCuentaUTE;
import org.tallerjava.moduloClientes.interfase.evento.out.ClienteNuevaTarjeta;
import org.tallerjava.moduloClientes.interfase.evento.out.ClienteNuevoCliente;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class ObserverModuloCliente {
    @Inject
    private ServicioCarga servicioCarga;

    public void accept(@Observes ClienteNuevoCliente event){
        Cliente cliente = new Cliente(
            event.getCedula(),
            event.getNombreCompleto(),
            event.getTelefono(),
            event.getContrasenia(),
            event.getPorcentajeDescuento()
        );

        servicioCarga.altaCliente(cliente);
    }

    public void accept(@Observes ClienteNuevaTarjeta event){
        MedioPago medioPago = new MedioPago(event.getId());
        servicioCarga.altaMedioPago(medioPago);
    }

    public void accept(@Observes ClienteNuevaCuentaUTE event){
        MedioPago medioPago = new MedioPago(event.getId());
        servicioCarga.altaMedioPago(medioPago);
    }
}
