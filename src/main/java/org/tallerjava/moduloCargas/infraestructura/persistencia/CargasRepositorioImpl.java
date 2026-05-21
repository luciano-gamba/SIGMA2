package org.tallerjava.moduloCargas.infraestructura.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.repo.CargasRepositorio;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class CargasRepositorioImpl implements CargasRepositorio{
    
    @PersistenceContext
    private EntityManager em;

    public void guardarEstacion(EstacionCarga estacionCarga){
        em.persist(estacionCarga);
    }

    public void guardarCargador(Cargador cargador){
        em.persist(cargador);
        em.merge(cargador.getMiEstacionCarga());
    }

    public void guardarCarga(Carga carga) {
        em.persist(carga);
        em.persist(carga.getMiPago());
        em.merge(carga.getMiCliente());
        em.merge(carga);
        em.merge(carga.getCargador());
    }

    public List<EstacionCarga> obtenerEstaciones() {
        String sql = "SELECT DISTINCT e FROM estacionCarga e LEFT JOIN FETCH e.misCargadores" ; //se usa el entity name Select ec from estacionCarga ec

        TypedQuery<EstacionCarga> obtenerEstaciones = em.createQuery(sql,EstacionCarga.class);
        try{
            return obtenerEstaciones.getResultList();
        }catch(NoResultException e){
            return null;
        }
    }

    public Cliente getCliente(String cedula){
        String sql = "Select c from ClienteCarga c where c.cedula = :cedula" ;
        
        TypedQuery<Cliente> findById = em.createQuery(sql,Cliente.class).setParameter("cedula", cedula);
        try {
            return findById.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void guardarCliente(Cliente cliente) {
        em.persist(cliente);
    }

    public Cargador getCargador(int idCargador){
        String sql = "Select c from Cargador c where c.id = :idCargador" ;

        TypedQuery<Cargador> findById = em.createQuery(sql,Cargador.class).setParameter("id", idCargador);
        try {
            return findById.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public EstacionCarga getEstacion(int idEstacion){
        String sql = "Select ec from estacionCarga ec where ec.id = :idEstacion" ;

        TypedQuery<EstacionCarga> findById = em.createQuery(sql,EstacionCarga.class).setParameter("id", idEstacion);
        try {
            return findById.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void guardarFinalizacionCarga(Cliente cliente, Carga carga, Cargador cargador) {
        em.merge(cliente);
        em.merge(carga);
        em.merge(cargador);
    }
}
