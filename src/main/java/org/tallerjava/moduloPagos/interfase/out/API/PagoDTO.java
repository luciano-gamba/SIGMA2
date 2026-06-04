package org.tallerjava.moduloPagos.interfase.out.API;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.tallerjava.moduloPagos.dominio.Pago;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO {

    private int id;
    private String idMedioPago;
    private String cedulaCliente;
    private LocalDateTime fechaHoraPago;
    private Float importe;

    public PagoDTO(Pago p){
        this.id = p.getId().intValue();
        this.idMedioPago = p.getMedioPago().getId().toString();
        this.cedulaCliente = p.getCliente().getCedula();
        this.fechaHoraPago = p.getFechaHoraPago();
        this.importe = p.getImporte();
    }
}
