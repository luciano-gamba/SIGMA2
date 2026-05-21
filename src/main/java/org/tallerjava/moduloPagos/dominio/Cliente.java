package org.tallerjava.moduloPagos.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "moduloPagos_EntityCliente")
@Table(name = "moduloPagos_CLIENTE")
public class Cliente {

    @Id
    private String cedula;
    private String nombreCompleto;

}
