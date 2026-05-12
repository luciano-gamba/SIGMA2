package org.tallerjava.moduloCargas.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cargador {
//    private TipoCargador tipoCargador;
    private boolean tieneCable;
//    private TipoConector tipoConector;
    private int estadoCargador;
    private LocalDateTime tiempoEstimadoFinalizacion; //si estadoCargador = ocupado
    private LocalDate fechaEstimadaReparacion; //si estadoCargador = fueraDeServicio
    private int potenciaMinima; //si tipoCargador = rapida

    private Carga cargaActiva; //si estadoCargador = ocupado
    private List<Carga> historialCargas;

}
