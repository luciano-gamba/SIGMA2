package org.tallerjava.moduloCargas.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ClienteCarga")
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloCarga_CLIENTE")

public class Cliente {

    @Id
    protected String cedula;

    protected String nombreCompleto;
    protected String telefono;
    protected String contrasenia;

    @OneToOne
    @JoinColumn(name = "carga_activa_id")
    private Carga cargaActiva;

    @OneToMany(mappedBy = "miCliente")
    private List<Carga> historialCargas;

    public Cliente(String cedula, String nombreCompleto, String telefono, String contrasenia) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
    }

}
