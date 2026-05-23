package org.tallerjava.moduloCargas.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MedioPagoCarga")
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloCarga_MEDIOPAGO")
public class MedioPago {

    @Id
    private long id;

}
