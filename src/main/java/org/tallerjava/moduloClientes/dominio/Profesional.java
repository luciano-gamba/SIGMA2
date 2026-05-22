package org.tallerjava.moduloClientes.dominio;

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
@Entity(name = "ProfesionalClientes")

@Table(name = "moduloClientes_CLIENTE_PROFESIONAL")

public class Profesional extends Cliente {

    private double porcentajeDescuento;
    private EnumTipoProfesional tipo;

}
