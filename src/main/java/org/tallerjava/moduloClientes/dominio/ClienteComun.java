package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor

@Table(name = "moduloClientes_CLIENTE_COMUN")
public class ClienteComun extends Cliente {

}
