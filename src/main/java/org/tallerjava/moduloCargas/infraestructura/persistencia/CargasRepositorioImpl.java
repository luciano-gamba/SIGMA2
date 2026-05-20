package org.tallerjava.moduloCargas.infraestructura.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.tallerjava.moduloCargas.dominio.*;
import org.tallerjava.moduloCargas.dominio.repo.CargasRepositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class CargasRepositorioImpl implements CargasRepositorio{
    
    @PersistenceContext
    private EntityManager em;

    public void guardarEstacion(EstacionCarga estacionCarga){
        em.persist(estacionCarga);
    }

    public void guardarCargador(Cargador cargador){
        em.persist(cargador);
    }

    public void guardarCarga(Carga carga, Cliente cliente){
        em.persist(carga);
        em.merge(cliente);
    }

    public List<EstacionCarga> obtenerEstaciones() {
        String sql = "Select ec from estacionCarga ec" ; //se usa el entity name 

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

}
