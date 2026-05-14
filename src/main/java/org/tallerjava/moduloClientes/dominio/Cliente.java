package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ClienteClientes") // Como quieran esto es un nombre interno yo lo entiendo mas con ClienteClientes
// pero si quieren puede ser ClienteModulo para indicar que este Cliente es el
// del moduloCliente da igual supongo
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloClientes_CLIENTE")

public class Cliente {

    @Id
    protected String cedula;

    protected String nombreCompleto;
    protected String telefono;
    protected String contrasenia;

}
