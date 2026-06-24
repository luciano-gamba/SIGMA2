package org.tallerjava.moduloClientes.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@Table(name = "moduloClientes_RECLAMO")
// esto lo voy a modificar en la tercera entrega.
// no va a quedar asi, es para poner algo de momento;
public class Reclamo {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String ci;
    private String comentario;
    private LocalDate fecha;
    private String clasificacion;

    public Reclamo(ReclamoRealizadoMessage r, String clasificacion) {
        this.ci = r.cedula();
        this.comentario = r.mensaje();
        this.fecha = r.fechaReclamo();
        this.clasificacion = clasificacion;
    }
}