package org.tallerjava.moduloPagos.dominio;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "MEIDOPAGO_TARJETA")
public class Tarjeta extends MedioPago{

    private String numero;
    private LocalDate fechaVencimiento;
    private String digitoVerificador;
    //private EnumTipoTarjeta tipoTarjeta;
    
}
