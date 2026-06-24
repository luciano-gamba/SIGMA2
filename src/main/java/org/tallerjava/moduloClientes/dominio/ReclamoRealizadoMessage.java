package org.tallerjava.moduloClientes.dominio;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;

public record ReclamoRealizadoMessage(
        String cedula,
        String mensaje,
        LocalDate fechaReclamo
) {

    public String toJson() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("cedula", this.cedula)
                .add("mensaje", this.mensaje)
                .add("fechaReclamo", this.fechaReclamo.toString()).build();

        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(sw);
        jsonWriter.write(jsonObject);
        jsonWriter.close();
        return sw.toString();
    }

    public static ReclamoRealizadoMessage buildFromJson(String jsonReclamoRealizado) {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonReclamoRealizado));
        JsonObject objeto = jsonReader.readObject();
        return new ReclamoRealizadoMessage(
                objeto.getString("cedula"),
                objeto.getString("mensaje"),
                LocalDate.parse(objeto.getString("fechaReclamo"))
        );
    }
}
