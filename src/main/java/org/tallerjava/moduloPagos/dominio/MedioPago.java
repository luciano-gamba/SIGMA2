package org.tallerjava.moduloPagos.dominio;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "MEDIOPAGO")
public class MedioPago {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

}
