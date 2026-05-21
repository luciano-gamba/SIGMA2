package org.tallerjava.moduloPagos.dominio;

import java.time.LocalDate;

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
@Entity(name = "moduloPagos_EntityTarjeta")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "moduloPagos_MEDIOPAGO_TARJETA")
public class Tarjeta extends MedioPago{

    private String numero;
    private LocalDate fechaVencimiento;
    private String digitoVerificador;    
}
