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
    // private LocalDate fecha; Nos parece inecesario el atributo fecha de Carga
    // porque podemos tomar cuando se realizo la carga conviertiendo el horaInicio
    // en LocalDate
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private float importeTotal;
    private float recargoPorDemora;
    private int porcentajeAvance; //0-100 si estadoCarga = activa
    private LocalDateTime horaEstimadaFin; //si estadoCarga = activa
    private boolean estadoCarga;


}
