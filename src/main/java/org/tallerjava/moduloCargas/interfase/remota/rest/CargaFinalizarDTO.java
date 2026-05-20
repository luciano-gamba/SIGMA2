package org.tallerjava.moduloCargas.interfase.remota.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargaFinalizarDTO {
    private int idCarga;
//    private String cedula; // como encuentro la carga?? con cedula del cliente?
    private double tiempoRecargo;
}
