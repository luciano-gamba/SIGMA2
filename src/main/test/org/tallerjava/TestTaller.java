package org.tallerjava;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.Bean;
import org.jboss.weld.junit.MockBean;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.tallerjava.moduloCargas.aplicacion.impl.ServicioCargaImpl;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.dominio.repo.CargasRepositorio;
import org.tallerjava.moduloCargas.interfase.evento.out.PublicadorEventoCarga;
import org.tallerjava.moduloCargas.test.CargasRepositorioFake;
import org.tallerjava.moduloClientes.dominio.*;
import org.tallerjava.moduloClientes.aplicacion.impl.ServicioClientesImpl;
import org.tallerjava.moduloClientes.dominio.repo.ClientesRepositorio;
import org.tallerjava.moduloClientes.interfase.evento.out.PublicadorEventoCliente;

import java.util.List;

@EnableWeld
class TestTaller {

    /**
     * Como funcionan los test de la capa de aplicación.
     * Cada implementación (en este caso ServicioPeajeImpl) tiene sus dependencias
     * las cuales son injectadas por el contender (ejemplo: repositorio,
     * serviciosPagodFacade, publicadorEventos)
     *
     * El objetivo de cada test es probar el código del caso de uso, no el código de
     * sus dependencias, por lo tanto,
     * cuando ejecuto los test tengo que brindar objetos mock (fakes) que devuelvan
     * lo que yo quiero.
     *
     * Lo primero es configurar Weld para que use dichos objetos fakes.
     *
     * NOTA: hacer test lleva tiempo, probar todos los casos es deseable en un
     * proyecto real
     * pero es una activad que consume muchos recursos, por lo que en este
     * instancia, solo haremos los principales
     *
     */
    @WeldSetup
    public WeldInitiator weld = WeldInitiator
            .from( ServicioClientesImpl.class, ServicioCargaImpl.class)
            .addBeans(crearMockClientesRepositorio())
            .addBeans(crearMockPublicadorEventoCliente())
            .addBeans(crearMockCargaRepositorio())
            .addBeans(crearMockPublicadorEventoCarga())
            .build();

    /**
     * Esta es la manera en que el framework de dependencias y junit necesita que
     * construyamos los
     * mock para cada dependencia
     * 
     * @return
     */
   

    private Bean<?> crearMockClientesRepositorio() {
        return MockBean.builder()
                .types(ClientesRepositorio.class)
                .scope(ApplicationScoped.class)
                .creating(crearClientesRepoImpl())
                .build();
    }

    // Hecho por Lucas pero tal vez preferis cambiar algunas cosas Nahuel era pq
    // queria poder usar los test
    private Bean<?> crearMockPublicadorEventoCliente() {
        return MockBean.builder()
                .types(PublicadorEventoCliente.class)
                .scope(ApplicationScoped.class)
                .creating(new PublicadorEventoCliente() {
                    @Override
                    public void publicarNuevoCliente(Cliente cliente, double descuento) {
                        System.out.println("Disparo nuevo cliente para que otros modulos lo detecten con observadores");
                    }

                    @Override
                    public void publicarNuevaTarjeta(ClienteTarjeta tarjeta) {
                        System.out.println(
                                "Disparo evento con la nueva tarjeta que cree para que los interesados lo observen");
                    }

                    @Override
                    public void publicarNuevaCuentaUTE(CuentaUTE cuenta) {
                        System.out.println(
                                "Disparo evento con la nueva cuentaUTE que cree para que los interesados lo observen");
                    }
                })
                .build();
    }

    private Bean<?> crearMockCargaRepositorio() {
        return MockBean.builder()
                .types(CargasRepositorio.class)
                .scope(ApplicationScoped.class)
                .creating(crearCargasRepoImpl())
                .build();
    }


    private Bean<?> crearMockPublicadorEventoCarga(){
        return MockBean.builder()
        .types(PublicadorEventoCarga.class)
        .scope(ApplicationScoped.class)
        .creating(new PublicadorEventoCarga())
        .build();
    }
    /**
     * Aca construyo un implementación "fake" del repositorio que me devuelve un
     * datos concreto
     * relevante para ejecutar este test
     * 
     * @return
     */

    private ClientesRepositorio crearClientesRepoImpl() {
        return new ClientesRepositorio() {

            @Override
            public void guardarCliente(Cliente cliente) {
                // fake
                System.out.println("sirvo para algo??");
            }

            @Override
            public List<Cliente> obtenerClientes() {
                return List.of();
            }

            @Override
            public Cliente getCliente(String ci, String contrasenia) {
                return null;
            }

            @Override
            public void altaMedioPago(Cliente cliente, MedioPago medioPago) {

            }

            @Override
            public Cliente getClienteSC(String ci) {
                return null;
            }

            @Override
            public void guardarReclamo(Reclamo reclamo) {

            }

            @Override
            public List<MedioPago> getMediosPago() {
                return List.of();
            }
        };
    }

    private CargasRepositorio crearCargasRepoImpl() {
        return new CargasRepositorioFake() {

            @Override
            public void guardarEstacion(EstacionCarga estacionCarga) {
                System.out.println("Guardando estacion");
                System.out.println(estacionCarga.getDescripcion() + " " + estacionCarga.getDepartamento());
            }

        };
    }

    /**
     * Implementación "fake" del servicio de pagos, solo implemento este metodo
     * porque es lo
     * unico que preciso para probar este caso de pruebas.
     * 
     * @return
     *
     */
    
//    @Test
//    @DisplayName("Verifico usuario guardado")
//    void testearCliente(ServicioClientes servicioClientes) {
//        Cliente cliente = new ClienteComun();
//        servicioClientes.registrarCliente(cliente);
//    }
//
//    @Test
//    @DisplayName("Verifico estacion de carga guardada")
//    void testearCarga(ServicioCarga servicioCarga) {
//        EstacionCarga estacion = new EstacionCarga("Estación en ANCAP San Carlos", "Alvariza y Treinta y tres",
//                "Maldonado", 150, 150);
//        servicioCarga.altaEstacion(estacion);
//    }


}
