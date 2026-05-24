package org.tallerjava.moduloCargas.dominio;

public enum EnumTipoCargador {

    Rapido(1),
    Lento(2);

    private int id;

    EnumTipoCargador(int id) { this.id = id; }

    public int getId() {
        return id;
    }

    public static EnumTipoCargador getById(int id) {
        switch (id) {
            case 1:
                return Rapido;
            case 2:
                return Lento;
            default:
                throw new IllegalArgumentException("Tipo de Cargador no reconocido");
        }
    }
}
