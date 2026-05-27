package org.tallerjava.moduloCargas.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "estacionCarga")

@Table(name = "moduloCarga_ESTACION_CARGA")

public class EstacionCarga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String descripcion;
    private String calle;
    private String departamento;
    private int longitud;
    private int latitud;

    @OneToMany(mappedBy = "miEstacionCarga")
    private List<Cargador> misCargadores;

    public EstacionCarga(String descripcion, String calle, String departamento, int longitud, int latitud) {
        this.descripcion = descripcion;
        this.calle = calle;
        this.departamento = departamento;
        this.longitud = longitud;
        this.latitud = latitud;
        this.misCargadores = new ArrayList<>();
    }

}
