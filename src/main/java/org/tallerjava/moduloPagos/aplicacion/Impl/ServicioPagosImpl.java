package org.tallerjava.moduloPagos.aplicacion.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.tallerjava.moduloPagos.dominio.CuentaUTE;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.aplicacion.ServicioPagos;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.Pago;
import org.tallerjava.moduloPagos.dominio.Tarjeta;
import org.tallerjava.moduloPagos.dominio.repositorio.PagosRepositorio;
import org.tallerjava.moduloPagos.interfase.out.PublicadorEventoPago;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ServicioPagosImpl implements ServicioPagos {

    @Inject
    private PublicadorEventoPago publicador;

    @Inject
    private PagosRepositorio repositorio;

    
    public void guardarCliente(String cedulaCliente, String nombreCompleto){
        Cliente cliente = new Cliente(cedulaCliente,nombreCompleto);
        repositorio.guardarCliente(cliente);
    }

    
    public void guardarCuentaUTE(Long id, String numeroCuenta){
        CuentaUTE medioPago = new CuentaUTE();
        medioPago.setId(id);
        medioPago.setNumeroCuenta(numeroCuenta); 
        repositorio.guardarMedioPago(medioPago);       
    }

    
    public void guardarTarjeta(Long id, String numero, LocalDate fechaVencimiento, String digitoVerificador){
        Tarjeta medioPago = new Tarjeta();
        medioPago.setId(id);
        medioPago.setNumero(numero);
        medioPago.setFechaVencimiento(fechaVencimiento);
        medioPago.setDigitoVerificador(digitoVerificador);
        repositorio.guardarMedioPago(medioPago);        
    }

    
    public void pagarCarga(String cedulaCliente, Float importe, Long idMedioPago) {
        
        MedioPago medioPago = repositorio.getMedioPago(idMedioPago);
        Cliente cliente = repositorio.getCliente(cedulaCliente);
        Pago pago = new Pago();
        pago.setCliente(cliente);
        pago.setMedioPago(medioPago);
        pago.setFechaHoraPago(LocalDateTime.now());
        pago.setImporte(importe);

        if (medioPago != null) {
            if (medioPago instanceof Tarjeta) {
                System.out.println("Comunicacion con el sistema externo de Tarjeta. \n" +
                        "\nCliente: " + cedulaCliente +
                        "\nImporte: " + importe.toString() +
                        "\n\nID Medio de Pago: " + ((Tarjeta) medioPago).getId() +
                        "\nNumero: " + ((Tarjeta) medioPago).getNumero() +
                        "\nFecha de Vencimiento: " + ((Tarjeta) medioPago).getFechaVencimiento() +
                        "\nDigito verificador: " + ((Tarjeta) medioPago).getDigitoVerificador());
                        
                // por ahora los pagos son siempre exitosos
                publicador.publicarEventoTarjeta(true, "Pago efectuado con éxito", cedulaCliente);
                pago.setAprobado(true);

            } else if (medioPago instanceof CuentaUTE) {
                System.out.println("Comunicacion con el sistema externo de CuentaUTE. \n" +
                        "\nCliente: " + cedulaCliente +
                        "\nImporte: " + importe.toString() +
                        "\n\nID Medio de Pago: " + ((CuentaUTE) medioPago).getId() +
                        "\nNumero: " + ((CuentaUTE) medioPago).getNumeroCuenta());

                // por ahora los pagos son siempre exitosos
                publicador.publicarEventoCuentaUTE(true, "Pago efectuado con éxito", cedulaCliente);
                pago.setAprobado(true);
            }
        }

        repositorio.guardarPago(pago);
    }

    
    public void consultarPagos(String cedulaCliente, LocalDate inicio, LocalDate fin) {
        repositorio.consultarPagos(cedulaCliente, inicio, fin);
    }

}