package org.tallerjava.moduloCargas.dominio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloCargas.dominio.Carga;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargaDTO {
    private int id;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private double importeTotal;
    private double recargoPorDemora;

    private int idCargador;
    private long idMedioPago;

    public CargaDTO(Carga c){
        this.id = c.getId();
        this.horaInicio = c.getHoraInicio();
        this.horaFin = c.getHoraFin();
        this.importeTotal = c.getImporteTotal();
        this.recargoPorDemora = c.getRecargoPorDemora();
        this.idCargador = c.getCargador().getId();
        this.idMedioPago = c.getMiPago().getId();
    }
}
