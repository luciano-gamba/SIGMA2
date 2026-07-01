package org.tallerjava.moduloPagos.aplicacion;
import java.time.LocalDate;
import java.util.List;

import org.tallerjava.moduloPagos.interfase.out.API.PagoDTO;

public interface ServicioPagos {
    
    void guardarCliente(String cedulaCliente, String nombreCompleto);
    void guardarCuentaUTE(Long id, String numeroCuenta);
    void guardarTarjeta(Long id, String numero, LocalDate fechaVencimiento, String digitoVerificador);
    
    void pagarCarga(String cedulaCliente, Float importe, Long idMedioPago);
    List<PagoDTO> consultarPagos(String cedulaCliente, LocalDate inicio, LocalDate fin);

}
