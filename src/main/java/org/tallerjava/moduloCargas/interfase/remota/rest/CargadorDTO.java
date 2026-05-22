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

    public Cargador buildCargador(){
        EnumTipoCargador enumCargador;
        if(this.tipoCargador == EnumTipoCargador.Rapido.getId()){
            enumCargador = EnumTipoCargador.Rapido;
        }else{
            enumCargador = EnumTipoCargador.Lento;
        }

        EnumTipoConector enumConector;
        switch(this.tipoConector){
            case 1:
                enumConector = EnumTipoConector.Tipo2;
                break;
            case 2:
                enumConector = EnumTipoConector.CCS2;
                break;
            case 3:
                enumConector = EnumTipoConector.CYHAdeMO;
                break;
            case 4:
                enumConector = EnumTipoConector.GB_T;
                break;
            default:
                enumConector = null;
        }

        Cargador cargador = new Cargador(enumCargador,this.tieneCable,enumConector,this.costePorHora);
        return cargador;
    }
}
