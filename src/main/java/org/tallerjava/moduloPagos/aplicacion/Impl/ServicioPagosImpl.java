package org.tallerjava.moduloPagos.aplicacion.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.tallerjava.moduloPagos.dominio.CuentaUTE;
import org.tallerjava.moduloPagos.dominio.Cliente;
import org.tallerjava.moduloPagos.aplicacion.ServicioPagos;
import org.tallerjava.moduloPagos.dominio.MedioPago;
import org.tallerjava.moduloPagos.dominio.Pago;
import org.tallerjava.moduloPagos.dominio.Tarjeta;
import org.tallerjava.moduloPagos.dominio.repositorio.PagosRepositorio;
import org.tallerjava.moduloPagos.interfase.out.PublicadorEventoPago;
import org.tallerjava.moduloPagos.interfase.out.API.PagoDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ServicioPagosImpl implements ServicioPagos {

    @Inject
    private PublicadorEventoPago publicador;

    @Inject
    private PagosRepositorio repositorio;

    public void guardarCliente(String cedulaCliente, String nombreCompleto) {
        Cliente cliente = new Cliente(cedulaCliente, nombreCompleto);
        repositorio.guardarCliente(cliente);
    }

    public void guardarCuentaUTE(Long id, String numeroCuenta) {
        CuentaUTE medioPago = new CuentaUTE();
        medioPago.setId(id);
        medioPago.setNumeroCuenta(numeroCuenta);
        repositorio.guardarMedioPago(medioPago);
    }

    public void guardarTarjeta(Long id, String numero, LocalDate fechaVencimiento, String digitoVerificador) {
        Tarjeta medioPago = new Tarjeta();
        medioPago.setId(id);
        medioPago.setNumero(numero);
        medioPago.setFechaVencimiento(fechaVencimiento);
        medioPago.setDigitoVerificador(digitoVerificador);
        repositorio.guardarMedioPago(medioPago);
    }

    public void pagarCarga(String cedulaCliente, Float importe, Long idMedioPago) {

        MedioPago medioPago = repositorio.getMedioPago(idMedioPago);
        Cliente cliente = repositorio.getCliente(cedulaCliente);
        Pago pago = new Pago();
        pago.setCliente(cliente);
        pago.setMedioPago(medioPago);
        pago.setFechaHoraPago(LocalDateTime.now());
        pago.setImporte(importe);

        if (medioPago != null) {
            if (medioPago instanceof Tarjeta) {
                Tarjeta t = repositorio.getTarjeta(idMedioPago);
                Client client = ClientBuilder.newClient();
                String url = "http://localhost:8080/SistemaExternoPAGOS/API/pagos/tarjeta";
                JsonObject jsonRequest = Json.createObjectBuilder()
                        .add("numero", t.getNumero())
                        .add("fechaVenciemiento", t.getFechaVencimiento().toString())
                        .add("digitoVerificador", t.getDigitoVerificador())
                        .add("importe", importe.floatValue())
                        .build();
                try {
                    Response response = client.target(url)
                            .request(MediaType.APPLICATION_JSON)
                            .post(Entity.entity(jsonRequest, MediaType.APPLICATION_JSON));
                    if (response.getStatus() == 200) {
                        JsonObject jsonResponse = response.readEntity(JsonObject.class);
                        publicador.publicarEventoTarjeta(true, jsonResponse.getString("mensaje"), cedulaCliente);
                        pago.setAprobado(true);
                        repositorio.guardarPago(pago);
                    } else {
                        publicador.publicarEventoTarjeta(false, response.toString(), cedulaCliente);
                    }
                    response.close();

                } catch (Exception e) {
                    System.err.println("Error al conectar con la API: " + e.getMessage());
                } finally {
                    client.close();
                }

            } else if (medioPago instanceof CuentaUTE) {
                CuentaUTE c = repositorio.getCuentaUTE(idMedioPago);
                Client client = ClientBuilder.newClient();
                String url = "http://localhost:8080/SistemaExternoPAGOS/API/pagos/cuentaute";
                JsonObject jsonRequest = Json.createObjectBuilder()
                        .add("numeroCuenta", c.getNumeroCuenta())
                        .build();
                try {
                    Response response = client.target(url)
                            .request(MediaType.APPLICATION_JSON)
                            .post(Entity.entity(jsonRequest, MediaType.APPLICATION_JSON));
                    if (response.getStatus() == 200) {
                        JsonObject jsonResponse = response.readEntity(JsonObject.class);
                        publicador.publicarEventoCuentaUTE(true, jsonResponse.getString("mensaje"), cedulaCliente);
                        pago.setAprobado(true);
                        repositorio.guardarPago(pago);
                    } else {
                        publicador.publicarEventoCuentaUTE(false, response.toString(), cedulaCliente);
                    }
                    response.close();

                } catch (Exception e) {
                    System.err.println("Error al conectar con la API: " + e.getMessage());
                } finally {
                    client.close();
                }

            }
        }
    }
    
    public List<PagoDTO> consultarPagos(String cedulaCliente, LocalDate inicio, LocalDate fin) {
        List<PagoDTO> pagoDTOs = new ArrayList<>();
        for (Pago pago : repositorio.consultarPagos(cedulaCliente, inicio, fin)) {
            pagoDTOs.add(new PagoDTO(pago));
        }
        return pagoDTOs;
    }

}