package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "TarjetaClientes")
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloClientes_MEIDOPAGO_TARJETA")

public class Tarjeta extends MedioPago{

    /*
    private String numero;
    private Date fechaVencimiento;
    private String digitoVerificador;
    //private EnumTipoTarjeta tipoTarjeta;
    */
}
