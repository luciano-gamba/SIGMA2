package org.tallerjava.moduloPagos.dominio.repositorio;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.Pago;

import java.time.LocalDate;
import java.util.List;

public interface PagosRepositorio {

    public void guardarCliente(Cliente cliente);
    public Cliente getCliente(String cedulaCliente);

    public void guardarMedioPago(MedioPago medioPago);
    public MedioPago getMedioPago(Long idMedioPago);

    public void guardarPago(Pago pago);
    public List<Pago> consultarPagos(String cedulaCliente, LocalDate ini, LocalDate fin);
}
