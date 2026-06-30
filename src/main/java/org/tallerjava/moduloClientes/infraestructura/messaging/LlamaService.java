package org.tallerjava.moduloClientes.infraestructura.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

@ApplicationScoped
public class LlamaService {

    private final HttpClient httpClient;

    // URL dinámica: Prioriza la variable de entorno 'OLLAMA_URL', si no está, usa tu ngrok actual por defecto
    private static final String OLLAMA_URL = System.getenv()
            .getOrDefault("OLLAMA_URL", "https://antiquity-similarly-eating.ngrok-free.dev/api/generate");

    public LlamaService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public String enviarPrompt(String prompt) throws IOException, InterruptedException {

        // 1. Construir el JSON usando la API estándar de Jakarta
        String jsonBody = Json.createObjectBuilder()
                .add("model", "llama3.1")
                .add("prompt", prompt)
                .add("stream", false)
                .build()
                .toString();

        // 2. Armar la petición HTTP POST con el bypass para ngrok
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_URL))
                .header("Content-Type", "application/json")
                .header("ngrok-skip-browser-warning", "true") // Evita la pantalla de advertencia de ngrok
                .timeout(Duration.ofMinutes(2))
                .POST(BodyPublishers.ofString(jsonBody))
                .build();

        // 3. Enviar la petición
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        // 4. Procesar la respuesta
        if (response.statusCode() == 200) {
            // Leer el JSON usando el parser nativo de Jakarta
            try (JsonReader jsonReader = Json.createReader(new StringReader(response.body()))) {
                JsonObject rootNode = jsonReader.readObject();
                return rootNode.getString("response"); // Extrae directamente el texto de Llama 2
            }
        } else {
            throw new RuntimeException("Error en Ollama/Ngrok. Status: " + response.statusCode() + " - " + response.body());
        }
    }
}