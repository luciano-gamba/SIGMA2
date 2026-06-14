package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.Entity;
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
@Entity(name = "ProfesionalClientes")

@Table(name = "moduloClientes_CLIENTE_PROFESIONAL")

public class Profesional extends Cliente {

    private double porcentajeDescuento;
    private EnumTipoProfesional tipo;

}
