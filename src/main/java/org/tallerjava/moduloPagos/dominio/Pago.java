package org.tallerjava.moduloPagos.dominio;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "moduloPagos_EntityPago")
@Table(name = "moduloPagos_PAGO")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cedula_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_medioPago")
    private MedioPago medioPago;

    private LocalDateTime fechaHoraPago;
    private Float importe;
    private Boolean aprovado;

}
