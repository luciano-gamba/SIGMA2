package org.tallerjava.moduloPagos.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ClientePagos")
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloPagos_CLIENTE")
public class Cliente {

    @Id
    protected String cedula;

    protected String nombreCompleto;
    protected String telefono;
    protected String contrasenia;

}
