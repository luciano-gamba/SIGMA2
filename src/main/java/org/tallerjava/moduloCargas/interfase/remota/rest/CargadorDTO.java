package org.tallerjava.moduloCargas.interfase.remota.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloCargas.dominio.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargadorDTO {
    private int tipoCargador;
    private boolean tieneCable;
    private int tipoConector;
    private double costePorHora;
    private int miEstacionCarga;

    public CargadorDTO(Cargador c) {
    }

    public Cargador buildCargador(){
        EnumTipoCargador enumCargador = EnumTipoCargador.getById(this.tipoCargador);
        EnumTipoConector enumConector = EnumTipoConector.getById(this.tipoConector);

        return new Cargador(enumCargador,this.tieneCable,enumConector,this.costePorHora);
    }
}
