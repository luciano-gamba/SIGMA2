package org.tallerjava.moduloPeaje.aplicacion.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.Bean;
import org.jboss.weld.junit.MockBean;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.tallerjava.moduloGestion.interfase.local.ServicioPagoFacade;
import org.tallerjava.moduloPeaje.dominio.*;
import org.tallerjava.moduloPeaje.dominio.repo.PeajeRepositorio;
import org.tallerjava.moduloPeaje.infraestructura.persistencia.PeajeRepositorioImpl;
import org.tallerjava.moduloPeaje.interfase.evento.out.PublicadorEvento;
import org.tallerjava.moduloCargas.aplicacion.ServicioCarga;
import org.tallerjava.moduloCargas.aplicacion.impl.ServicioCargaImpl;
import org.tallerjava.moduloCargas.dominio.Carga;
import org.tallerjava.moduloCargas.dominio.Cargador;
import org.tallerjava.moduloCargas.dominio.EstacionCarga;
import org.tallerjava.moduloCargas.dominio.repo.CargasRepositorio;
import org.tallerjava.moduloCargas.test.CargasRepositorioFake;
import org.tallerjava.moduloClientes.aplicacion.ServicioClientes;
import org.tallerjava.moduloClientes.dominio.*;
import org.tallerjava.moduloClientes.aplicacion.impl.ServicioClientesImpl;
import org.tallerjava.moduloClientes.dominio.repo.ClientesRepositorio;
import org.tallerjava.moduloClientes.interfase.evento.out.PublicadorEventoCliente;

import java.util.ArrayList;
import java.util.List;

@EnableWeld
class VerificoTagExtranjeroConPrePagoOk {

    /**
     * Como funcionan los test de la capa de aplicación.
     * Cada implementación (en este caso ServicioPeajeImpl) tiene sus dependencias
     * las cuales son injectadas por el contender (ejemplo: repositorio, serviciosPagodFacade, publicadorEventos)
     *
     * El objetivo de cada test es probar el código del caso de uso, no el código de sus dependencias, por lo tanto,
     * cuando ejecuto los test tengo que brindar objetos mock (fakes) que devuelvan lo que yo quiero.
     *
     * Lo primero es configurar Weld para que use dichos objetos fakes.
     *
     * NOTA: hacer test lleva tiempo, probar todos los casos es deseable en un proyecto real
     * pero es una activad que consume muchos recursos, por lo que en este instancia, solo haremos los principales
     *
     */
    @WeldSetup
    public WeldInitiator weld =
            WeldInitiator.from(ServicioPeajeImpl.class, ServicioClientesImpl.class, ServicioCargaImpl.class)
                    .addBeans(crearMockRepositorioImpl())
                    .addBeans(crearMockServiciosPagosFacade())
                    .addBeans(crearMockPublicadorEvento())
                    .addBeans(crearMockClientesRepositorio())
                    .addBeans(crearMockPublicadorEventoCliente())
                    .addBeans(crearMockCargaRepositorio())
                    .build();


    /**
     * Esta es la manera en que el framework de dependencias y junit necesita que construyamos los
     * mock para cada dependencia
     * @return
     */
    private Bean<?> crearMockRepositorioImpl() {
        return MockBean.builder()
                .types(PeajeRepositorio.class) //esto lo saco del @inject de ServicioPeajeImpl
                .scope(ApplicationScoped.class)
                .creating(crearRepoImpl())  //aca construyo la implementación que será usasa en este test
                .build();
    }

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
                    public void publicarNuevoCliente(Cliente cliente) {
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

    private Bean<?> crearMockServiciosPagosFacade() {
        return MockBean.builder()
                .types(ServicioPagoFacade.class)
                .scope(ApplicationScoped.class)
                .creating(
                        crearServicioPagoFacade()
                ).build();
    }

    private Bean<?> crearMockPublicadorEvento() {
        return MockBean.builder()
                .types(PublicadorEvento.class)
                .scope(ApplicationScoped.class)
                .creating(
                        new PublicadorEvento()
                ).build();
    }


    /**
     * Aca construyo un implementación "fake" del repositorio que me devuelve un datos concreto
     * relevante para ejecutar este test
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
        };
    }

    private CargasRepositorio crearCargasRepoImpl() {
        return new CargasRepositorioFake() {

            @Override
            public void guardarEstacion(EstacionCarga estacionCarga) {
                System.out.println("Guardando estacion");
            }

        };
    }

    private PeajeRepositorio crearRepoImpl() {
        return new PeajeRepositorioImpl() {

            @Override
            public Vehiculo findByTag(int tag) {
                Vehiculo vehiculo = new Vehiculo(1,
                        new Identificador("BAA 1111", tag),
                        "ford", "fiesta", Nacionalidad.EXTRANJERO);
                return vehiculo;
            }


            @Override
            public Preferencial obtenerTarifaPreferencial() {
                return new Preferencial();
            }

            @Override
            public Comun obtenerTarifaComun() {
                return new Comun();
            }
        };
    }

    /**
     * Implementación "fake" del servicio de pagos, solo implemento este metodo porque es lo
     * unico que preciso para probar este caso de pruebas.
     * @return
     *
     */
    private ServicioPagoFacade crearServicioPagoFacade() {
        return new ServicioPagoFacade() {
            public boolean realizarPrePago(int tag, double importe) {
                //Estoy provando que el pago es aceptdo, por eso retorno true.
                //Eventualmente debería de crear otro test para probar los casos donde se devuelve false
                return true;
            }
        };
    }

    @Test
    @DisplayName("Verifico tag de v. extranjero con PrePago confirmado ok")
    void estaHabilitadoAutoExtranjeroConTagPrePagoOk(ServicioPeajeImpl servicioPeaje) {
        Assertions.assertTrue(
                //obervese que los valores pasados como parámetros tiene poca relevancia
                //en este test, ya que el vehiculo recuperado desde el fake de repositorio
                //(implementado arriba) siempre devolverá un vehiculo con tag hardcoded.
                servicioPeaje.estaHabilitadoSincronico(10001,"BAA 1111"));
    }

    @Test
    @DisplayName("Verifico usuario guardado")
    void testearCliente(ServicioClientes servicioClientes) {
        Cliente cliente = new ClienteComun();
                servicioClientes.registrarCliente(cliente);
    }

    @Test
    @DisplayName("Verifico estacion de carga guardada")
    void testearCliente(ServicioCarga servicioCarga) {
        EstacionCarga estacion = new EstacionCarga("Estación en ANCAP San Carlos", "Alvariza y Treinta y tres",
                "Maldonado", 150, 150);
        servicioCarga.altaEstacion(estacion);
    }

}
