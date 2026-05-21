package org.tallerjava.moduloPagos.infraestructura.persistencia;

import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.Pago;
import org.tallerjava.moduloPagos.dominio.repositorio.PagosRepositorio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PagosRepositorioImpl implements PagosRepositorio {

    @PersistenceContext
    private EntityManager em;

    public void guardarCliente(Cliente cliente) {
        em.persist(cliente);
    }

    public Cliente getCliente(String cedulaCliente) {
        return em.find(Cliente.class, cedulaCliente);
    }

    public void guardarMedioPago(MedioPago medioPago) {
        em.persist(medioPago);
    }

    public MedioPago getMedioPago(Long idMedioPago) {
        return em.find(MedioPago.class, idMedioPago);
    }

    public void guardarPago(Pago pago) {
        em.persist(pago);
    }

    public List<Pago> consultarPagos(String cedulaCliente, LocalDate ini, LocalDate fin) {
        String jpql = "SELECT m FROM moduloPagos_EntityPago m WHERE m.cliente.cedula = :cedula AND m.fechaPago BETWEEN :inicio AND :fin";
        return em.createQuery(jpql, Pago.class)
                .setParameter("cedula", cedulaCliente)
                .setParameter("inicio", ini.atStartOfDay())
                .setParameter("fin", fin.atStartOfDay())
                .getResultList();
    }
}