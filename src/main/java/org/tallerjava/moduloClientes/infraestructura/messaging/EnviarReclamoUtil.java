package org.tallerjava.moduloClientes.infraestructura.messaging;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@ApplicationScoped
public class EnviarReclamoUtil {
    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:jboss/exported/jms/queue/servicioReclamo")
    private Queue queueReclamosRealizados;

    public void enviarMensaje(String mensaje) {
        jmsContext.createProducer().send(queueReclamosRealizados, mensaje);
    }
}
