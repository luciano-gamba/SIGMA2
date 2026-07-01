package org.tallerjava.moduloClientes.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.MedioPago;
import org.tallerjava.moduloClientes.dominio.Reclamo;
import org.tallerjava.moduloClientes.dominio.repo.ClientesRepositorio;

import java.util.List;

@ApplicationScoped
public class ClientesRepositorioImpl implements ClientesRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void guardarCliente(Cliente cliente){
        em.persist(cliente);
    }

    public List<Cliente> obtenerClientes(){
        String sql = "SELECT c FROM ClienteClientes c";

        TypedQuery<Cliente> obtenerClientes = em.createQuery(sql, Cliente.class);
        try {
            return obtenerClientes.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Cliente getCliente(String ci, String contrasenia){
        Cliente c = em.find(Cliente.class, ci);
        if (c == null){
            return null;
        }
        if (c.getContrasenia().equals(contrasenia)) {
            return c;
        } else {
            return null;
        }
    }

    public void altaMedioPago(Cliente cliente, MedioPago medioPago){
        em.persist(medioPago);
        em.merge(cliente);
    }

    public Cliente getClienteSC(String ci){
        return em.find(Cliente.class, ci);
    }

    @Override
    public void guardarReclamo(Reclamo reclamo){
        em.persist(reclamo);
    }

    @Override
    public List<MedioPago> getMediosPago(){
        String sql = "SELECT m FROM MedioPagoClientes m";

        TypedQuery<MedioPago> obtenerMedioPagos = em.createQuery(sql, MedioPago.class);
        try {
            return obtenerMedioPagos.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
