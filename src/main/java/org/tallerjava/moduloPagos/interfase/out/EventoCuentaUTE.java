package org.tallerjava.moduloPagos.interfase.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventoCuentaUTE {
    private boolean aprovado;
    private String mensaje;
    private String cedula;
}
