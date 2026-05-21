package org.tallerjava.moduloCargas.interfase.remota.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargaFinalizarDTO {
    private int idCargador;
    private double tiempoRecargo;
}
