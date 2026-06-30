package org.tallerjava.moduloClientes.infraestructura.messaging;

public class ReclamoResponse {
    private String textoOriginal;
    private String prioridad;

    public ReclamoResponse(String textoOriginal, String prioridad) {
        this.textoOriginal = textoOriginal;
        this.prioridad = prioridad;
    }
    // Getters
    public String getTextoOriginal() { return textoOriginal; }
    public String getPrioridad() { return prioridad; }
}