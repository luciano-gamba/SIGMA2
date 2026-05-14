package org.tallerjava.moduloClientes.infraestructura.persistencia;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.repo.ClientesRepositorio;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClientesRepositorioImpl implements ClientesRepositorio {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void guardarCliente(Cliente cliente){
        System.out.println("EN TEORIA y digo EN TEORIA!!!!, quedo guardado en la base de datos.");
        em.persist(cliente);
    }

    public List<Cliente> obtenerClientes(){
        String sql = "select * from Clientes";
        List<Cliente> c = new ArrayList<>();
        return c;
    }

}
