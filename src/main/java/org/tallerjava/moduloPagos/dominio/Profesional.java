package org.tallerjava.moduloPagos.dominio;

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
@Entity(name = "ProfesionalPagos")
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloPagos_CLIENTE_PROFESIONAL")
public class Profesional extends Cliente {
    private Float porcentajeDescuento;
}
