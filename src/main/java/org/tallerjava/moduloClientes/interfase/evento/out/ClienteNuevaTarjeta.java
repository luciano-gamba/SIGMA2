package org.tallerjava.moduloClientes.interfase.evento.out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ClienteNuevaTarjeta {

    private long id;
    private String numero;
    private LocalDate fechaVencimiento;
    private String digitoVerificador;

}
