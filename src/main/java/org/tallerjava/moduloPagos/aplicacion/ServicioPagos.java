package org.tallerjava.moduloPagos.aplicacion;
import java.time.LocalDate;
import java.util.List;

import org.tallerjava.moduloPagos.interfase.out.API.PagoDTO;

public interface ServicioPagos {
    
    public void guardarCliente(String cedulaCliente, String nombreCompleto);
    public void guardarCuentaUTE(Long id, String numeroCuenta);
    public void guardarTarjeta(Long id, String numero, LocalDate fechaVencimiento, String digitoVerificador);
    
    public void pagarCarga(String cedulaCliente, Float importe, Long idMedioPago);
    public List<PagoDTO> consultarPagos(String cedulaCliente, LocalDate inicio, LocalDate fin);

}
