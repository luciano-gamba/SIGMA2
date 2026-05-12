package org.tallerjava.moduloCargas.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstacionCarga {
    private int id;
    private String descripcion;
    private String calle;
    private String departamento;
    private int longitud;
    private int latitud;

    private List<Cargador> misCargadores;
}
