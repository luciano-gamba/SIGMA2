package org.tallerjava.moduloPagos.aplicacion;

import org.tallerjava.moduloPagos.dominio.MedioPago;

import java.time.LocalDate;

import org.tallerjava.moduloPagos.dominio.Cliente;

public interface ServicioPagos {
    
    public void pagarCarga(Cliente cliente, Float importe, MedioPago medioPago);
    public void consultarPagos(Cliente cliente, LocalDate inicio, LocalDate fin);

}
