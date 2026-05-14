package org.tallerjava.moduloPagos.aplicacion.Impl;

import java.time.LocalDate;

import org.tallerjava.moduloPagos.dominio.CuentaUTE;
import org.tallerjava.moduloPagos.aplicacion.ServicioPagos;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.Tarjeta;

public class ServicioPagosImpl implements ServicioPagos{

    //private PublicadorEvento publicadorEvento;

    public void pagarCarga(Cliente cliente, float importe, MedioPago medioPago){
        if (medioPago instanceof Tarjeta){
            Tarjeta t = (Tarjeta) medioPago;
            //publicar algo REST??

        }else if (medioPago instanceof CuentaUTE) {
            CuentaUTE c = (CuentaUTE) medioPago;
            //publicar algo REST??
        }
    }

    public void consultarPagos(Cliente cliente, LocalDate inicio, LocalDate fin){

    }

}