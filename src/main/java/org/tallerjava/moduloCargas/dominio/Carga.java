package org.tallerjava.moduloCargas.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carga {
    private LocalDate fecha;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private float importeTotal;
    private float recargoPorDemora;
    private int porcentajeAvance; //0-100 si estadoCarga = activa
    private LocalDateTime horaEstimadaFin; //si estadoCarga = activa
    private int estadoCarga;


}
