package org.tallerjava.moduloCargas.interfase.remota.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloCargas.dominio.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargaIniciarDTO {
    private int idCargador;
    private String cedula;
    private long idMedioPago;
}
