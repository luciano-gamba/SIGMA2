package org.tallerjava.moduloPagos.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "moduloPagos_EntityCuentaUTE")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "moduloPagos_MEDIOPAGO_CUENTAUTE")

public class CuentaUTE extends MedioPago{
    
    private String numeroCuenta;

}
