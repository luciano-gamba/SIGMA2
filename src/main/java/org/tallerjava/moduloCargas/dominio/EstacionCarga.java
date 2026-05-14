package org.tallerjava.moduloCargas.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity

@Table(name = "moduloCarga_ESTACION_CARGA")

public class EstacionCarga {
    @Id
    private int id;

    private String descripcion;
    private String calle;
    private String departamento;
    private int longitud;
    private int latitud;

    @OneToMany(mappedBy = "miEstacionCarga")
    private List<Cargador> misCargadores;
}
