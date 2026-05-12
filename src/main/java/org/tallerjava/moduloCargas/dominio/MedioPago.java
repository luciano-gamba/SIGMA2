package org.tallerjava.moduloCargas.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
