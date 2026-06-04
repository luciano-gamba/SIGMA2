package org.tallerjava.moduloPagos.interfase.out.API;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoDTO {
    private String cedula;
    private LocalDate inicio;
    private LocalDate fin;
}
