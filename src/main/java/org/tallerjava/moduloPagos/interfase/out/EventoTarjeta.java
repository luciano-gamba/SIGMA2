package org.tallerjava.moduloPagos.interfase.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventoTarjeta {
    private boolean aprobado;
    private String mensaje;
    private String cedula;
}
