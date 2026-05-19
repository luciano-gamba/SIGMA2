package org.tallerjava.moduloClientes.interfase.remota.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteMedioPagoDTO {

    private ClientesDTO cliente;
    private MedioPagoDTO medioPago;

}
