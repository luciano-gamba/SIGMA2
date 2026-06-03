package org.tallerjava.moduloClientes.infraestructura.seguridad.identitystore;

import jakarta.annotation.Priority;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.tallerjava.moduloClientes.dominio.Cliente;
import org.tallerjava.moduloClientes.dominio.repo.ClientesRepositorio;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Inject
    private ClientesRepositorio clientesRepositorio;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();

        if (method != null && method.isAnnotationPresent(RolesAllowed.class)) {

            String authorization = requestContext.getHeaderString(AUTHORIZATION_HEADER);

            if (authorization == null || authorization.isEmpty()) {
                abortar(requestContext, "Falta la cabecera de autenticacion (Basic Auth)");
                return;
            }

            String base64Credentials = authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            String[] split = credentials.split(":", 2);

            if (split.length != 2) {
                abortar(requestContext, "Formato de credenciales invalido");
                return;
            }

            String ci = split[0];
            String contraseniaPlana = split[1];

            System.out.println("=== FILTRO DE SEGURIDAD ===");
            System.out.println("Intentando login con Cédula: " + ci);

            Cliente cliente = clientesRepositorio.getClienteSC(ci);

            if (cliente == null) {
                System.out.println("Usuario NO existe");
                abortar(requestContext, "Credenciales incorrectas");
                return;
            }

            String hashGenerado = convertToHas(contraseniaPlana);
            if (!hashGenerado.equals(cliente.getContrasenia())) {
                System.out.println("Contraseña incorrecta");
                abortar(requestContext, "Credenciales incorrectas");
                return;
            }

            System.out.println("¡Login exitoso! Dejando pasar la petición...");
            System.out.println("===========================");
        }
    }

    // Método para cortar la petición y devolver un HTTP 401 Unauthorized
    private void abortar(ContainerRequestContext requestContext, String mensaje) {
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\": \"" + mensaje + "\"}")
                        .build()
        );
    }

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public static String convertToHas(String value) {
        try {
            return toHexString(getSHA(value));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}