package org.tallerjava.moduloClientes.infraestructura.messaging;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import org.tallerjava.moduloClientes.aplicacion.ServicioClientes;
import org.tallerjava.moduloClientes.dominio.ReclamoRealizadoMessage;

@MessageDriven(
        activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(
                propertyName = "destinationLookup",
                propertyValue = "java:app/jms/ServicioReclamoQueue"),
        @ActivationConfigProperty
                //Establece el número máximo de consumidores que estarán procesando
                //los mensajes
                //Por defecto este valor es 15 pero lo cambio a 1 para facilitar
                //la prueba que muestra su funcionamiento
                (propertyName = "maxSession", propertyValue = "1")
        }
)
public class NuevoReclamoConsumer implements MessageListener {

    @Inject
    private ServicioClientes servicio;

    public NuevoReclamoConsumer() {}

    @Override
    public void onMessage(Message message) {
        try {
            String body = message.getBody(String.class);

            //construyo nuevo objeto que representa un mensaje, a partir del string que recibo en el mensaje
            ReclamoRealizadoMessage reclamo = ReclamoRealizadoMessage.buildFromJson(body);
//            curl al IA (sacamos de la respuesta la clasificacion)

            servicio.guardarReclamo(reclamo, "todo bien");

        } catch (JMSException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}

