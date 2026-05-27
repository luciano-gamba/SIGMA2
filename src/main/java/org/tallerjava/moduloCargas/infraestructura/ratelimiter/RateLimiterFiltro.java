package org.tallerjava.moduloCargas.infraestructura.ratelimiter;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@RateLimited
@Provider
public class RateLimiterFiltro implements ContainerRequestFilter {

    @Inject
    private RateLimiter rateLimiter;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (rateLimiter.isActivo()) { //siempre true probablemente
            boolean sePermiteEjecutar = rateLimiter.consumir();
            if (!sePermiteEjecutar) { //si está vacio el bucket rechaza el request
                System.out.println("El servidor no acepta mensajes");
                requestContext.abortWith(Response.status(Response.Status.TOO_MANY_REQUESTS).entity("").build());
            }
            //else { el bucket tiene tokens, por lo que pasa tranqui }
        }

    }
}
