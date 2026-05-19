package org.tallerjava.moduloClientes.interfase.remota.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerjava.moduloClientes.dominio.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedioPagoDTO {

    private String numeroCuenta;
    private String numero;
    private LocalDate fechaVencimiento;
    private String digitoVerificador;

    public MedioPago buildMedioPago(){

        if(numero != null){

            ClienteTarjeta ct = new ClienteTarjeta();
            ct.setNumero(numero);
            ct.setFechaVencimiento(fechaVencimiento);
            ct.setDigitoVerificador(digitoVerificador);

            return ct;
        }

        CuentaUTE c = new CuentaUTE();
        c.setNumeroCuenta(numeroCuenta);

        return c;
    }

}
