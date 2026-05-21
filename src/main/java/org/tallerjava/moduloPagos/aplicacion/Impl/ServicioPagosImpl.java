package org.tallerjava.moduloPagos.aplicacion.Impl;

import java.time.LocalDate;

import org.tallerjava.moduloPagos.dominio.CuentaUTE;
import org.tallerjava.moduloPagos.dominio.Profesional;
import org.tallerjava.moduloPagos.aplicacion.ServicioPagos;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.Tarjeta;
import org.tallerjava.moduloPagos.interfase.out.PublicadorEventoConfirmacion;

import jakarta.inject.Inject;

public class ServicioPagosImpl implements ServicioPagos {

    @Inject
    private PublicadorEventoConfirmacion publicador;

    public void pagarCarga(Cliente cliente, Float importe, MedioPago medioPago) {
        if (medioPago instanceof Tarjeta) {

            if (cliente instanceof Profesional) {
                importe = importe - importe * ((Profesional) cliente).getPorcentajeDescuento();
            }

            System.out.println("Comunicacion con el sistema externo de Tarjeta. \n" +
                    "\nCliente: " + cliente.getCedula() +
                    "\nImporte: " + importe.toString() +
                    "\n\nID Medio de Pago: " + ((Tarjeta) medioPago).getId() +
                    "\nNumero: " + ((Tarjeta) medioPago).getNumero() +
                    "\nFecha de Vencimiento: " + ((Tarjeta) medioPago).getFechaVencimiento() +
                    "\nDigito verificador: " + ((Tarjeta) medioPago).getDigitoVerificador());

            // por ahora los pagos son siempre exitosos
            publicador.publicarEventoTarjeta(true, "Pago efectuado con éxito");

        } else if (medioPago instanceof CuentaUTE) {
            System.out.println("Comunicacion con el sistema externo de CuentaUTE. \n" +
                    "\nCliente: " + cliente.getCedula() +
                    "\nImporte: " + importe.toString() +
                    "\n\nID Medio de Pago: " + ((CuentaUTE) medioPago).getId() +
                    "\nNumero: " + ((CuentaUTE) medioPago).getNumeroCuenta());

            // por ahora los pagos son siempre exitosos
            publicador.publicarEventoCuentaUTE(true, "Pago efectuado con éxito");
        }
    }

    public void consultarPagos(Cliente cliente, LocalDate inicio, LocalDate fin) {
        
    }

}