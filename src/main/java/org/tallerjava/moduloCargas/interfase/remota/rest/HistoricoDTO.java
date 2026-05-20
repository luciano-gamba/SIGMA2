package org.tallerjava.moduloCargas.interfase.remota.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoDTO {
    private String cedula;
    private LocalDateTime inicio;
    private LocalDateTime fin;
}
