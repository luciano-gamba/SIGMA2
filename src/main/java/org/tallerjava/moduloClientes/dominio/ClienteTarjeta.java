package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "MEIDOPAGO_TARJETA")
public class ClienteTarjeta extends MedioPago{

    private String numero;
    private LocalDate fechaVencimiento;
    private String digitoVerificador;
    //private EnumTipoTarjeta tipoTarjeta; nos interesaba al final tener si es tarjeta de debito o de crédito?

}
