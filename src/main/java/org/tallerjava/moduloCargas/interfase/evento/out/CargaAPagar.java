package org.tallerjava.moduloCargas.interfase.evento.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CargaAPagar {
    private String cedula; //miCliente
    private double importeTotal;
    private long id; //miPago
}
