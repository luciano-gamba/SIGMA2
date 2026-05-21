package org.tallerjava.moduloPagos.dominio;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "moduloPagos_EntityMedioPago")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "moduloPagos_MEDIOPAGO")
public class MedioPago {

    @Id
    private Long id;

}
