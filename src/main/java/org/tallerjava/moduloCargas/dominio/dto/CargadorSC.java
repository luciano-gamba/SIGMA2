package org.tallerjava.moduloCargas.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloCargas.dominio.Cargador;
import org.tallerjava.moduloCargas.dominio.EnumTipoCargador;
import org.tallerjava.moduloCargas.dominio.EnumTipoConector;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargadorSC {
    private int estadoCargador;
    private EnumTipoCargador tipoCargador; // Rapida o Lenta
    private boolean tieneCable; // Si el cargador tiene cable o tenes que llevar el que te vino con el auto
    private EnumTipoConector tipoConector; // Tipo 2 , CCS2, CYHAdeMO, GB/T

    public CargadorSC (Cargador c){
        this.estadoCargador = c.getEstadoCargador();
        this.tipoCargador = c.getTipoCargador();
        this.tieneCable = c.isTieneCable();
        this.tipoConector = c.getTipoConector();
    }
}
