package org.tallerjava.moduloClientes.interfase.remota.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReclamoDTO {

    private String cedula;
    private String comentario;

}
