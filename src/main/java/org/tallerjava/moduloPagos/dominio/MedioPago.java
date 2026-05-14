package org.tallerjava.moduloPagos.dominio;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MedioPagoPagos")
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloPagos_MEDIOPAGO")
public class MedioPago {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

}
