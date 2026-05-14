package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "ComunClientes")
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloClientes_CLIENTE_COMUN")

public class Comun extends Cliente {

}
