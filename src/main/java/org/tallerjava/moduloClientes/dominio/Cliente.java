package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "ClienteClientes")
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

}
