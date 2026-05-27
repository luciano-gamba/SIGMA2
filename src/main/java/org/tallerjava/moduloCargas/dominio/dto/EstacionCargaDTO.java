package org.tallerjava.moduloCargas.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloCargas.dominio.Cargador;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstacionCargaDTO {
    private int id;

    private String descripcion;
    private String calle;
    private String departamento;
    private int longitud;
    private int latitud;

    private List<CargadorSC> misCargadores;

    public EstacionCargaDTO (EstacionCarga ec){
        this.id = ec.getId();
        this.calle = ec.getCalle();
        this.departamento = ec.getDepartamento();
        this.descripcion = ec.getDescripcion();
        this.longitud = ec.getLongitud();
        this.latitud = ec.getLatitud();

        this.misCargadores = new ArrayList<>();
        for(Cargador c : ec.getMisCargadores()){
            this.misCargadores.add(new CargadorSC(c));
        }
    }
}
