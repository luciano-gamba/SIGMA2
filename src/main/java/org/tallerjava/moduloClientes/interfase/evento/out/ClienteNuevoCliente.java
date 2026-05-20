package org.tallerjava.moduloClientes.interfase.evento.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClienteNuevoCliente {

    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private String contrasenia;

}
