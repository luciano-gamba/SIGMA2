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
    private LlamaService llamaService;

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

            String clasificacion = obtenerClasificacionIA(reclamo.mensaje());

            servicio.guardarReclamo(reclamo, clasificacion);

        } catch (JMSException e) {
            System.out.println(e.getLocalizedMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String obtenerClasificacionIA(String comentario) throws Exception {
        String prompt = """
            Actúa como un sistema automático de clasificación de soporte técnico. Clasifica el siguiente reclamo de un cliente en una de estas tres categorías: URGENTE, NORMAL, BAJA. Criterios de clasificación: URGENTE: Caídas de sistema, problemas de seguridad, pérdidas de datos, clientes extremadamente enojados exigiendo reembolsos inmediatos, cargador que no funciona, problemas con el pago.
            NORMAL: Errores en funciones de la app, dudas de facturación, problemas de acceso que no bloquean todo el sistema, estaciones de carga con cargadores lentos.
            BAJA: Sugerencias de mejora, preguntas frecuentes, saludos o felicitaciones. Responde ÚNICAMENTE con la palabra de la categoría (URGENTE, NORMAL o BAJA). No agregues introducciones, ni explicaciones, ni puntos, ni justificaciones. Reclamo: "%s"
            Categoría:
            """.formatted(comentario);

        String respuestaRaw = llamaService.enviarPrompt(prompt);
        String prioridad = respuestaRaw.trim().toUpperCase();

        if (!prioridad.equals("URGENTE") && !prioridad.equals("NORMAL") && !prioridad.equals("BAJA")) {
            if (prioridad.contains("URGENTE")) return "URGENTE";
            if (prioridad.contains("BAJA")) return "BAJA";
            return "NORMAL"; // Normal por defecto
        }
        return prioridad;
    }
}

