package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloClientes_MEDIOPAGO_TARJETA")
public class ClienteTarjeta extends MedioPago{

    private String numero;
    private LocalDate fechaVencimiento;
    private String digitoVerificador;

}
