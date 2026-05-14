package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MedioPagoClientes")
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloClientes_MEDIOPAGO")

public class MedioPago {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

}
