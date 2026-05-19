package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "ClienteClientes") // Como quieran esto es un nombre interno yo lo entiendo mas con ClienteClientes
// pero si quieren puede ser ClienteModulo para indicar que este Cliente es el
// del moduloCliente da igual supongo
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloClientes_CLIENTE")

public abstract class Cliente {

    @Id
    protected String cedula;

    protected String nombreCompleto;
    protected String telefono;
    protected String contrasenia;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected List<MedioPago> mediosDePago = new ArrayList<>();
    //tendria que haber una lista de medios de pago segun yo,
    //con un Enum no se cual tarjeta o cuentaUTE son las suyas.

}
