package org.tallerjava.moduloCargas.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "CLIENTE")
public class Cliente {

    @Id
    protected String cedula;

    protected String nombreCompleto;
    protected String telefono;
    protected String contrasenia;

    private Carga cargaActiva;
    private List<Carga> historialCargas;

}
