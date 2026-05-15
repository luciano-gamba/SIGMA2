package org.tallerjava.moduloPagos.aplicacion.Impl;

import java.time.LocalDate;

import org.tallerjava.moduloPagos.dominio.CuentaUTE;
import org.tallerjava.moduloPagos.aplicacion.ServicioPagos;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.Tarjeta;

public class ServicioPagosImpl implements ServicioPagos{

    public void pagarCarga(Cliente cliente, float importe, MedioPago medioPago){
        if (medioPago instanceof Tarjeta){
            System.out.println("comunicacion con el sistema externo de Tarjeta");

        }else if (medioPago instanceof CuentaUTE) {
            System.out.println("comunicacion con el sistema externo de CuentaUTE");
        }
    }

    public void consultarPagos(Cliente cliente, LocalDate inicio, LocalDate fin){
        
    }

} 