package org.tallerjava.moduloPagos.dominio;

public enum EnumTipoProfesional {

    TAXI(1),
    UBER(2),
    CABIFY(3);

    private int id;
    EnumTipoProfesional(int id) { this.id = id; }
    public int getId() { return id; }

    public static EnumTipoProfesional getById(int id) {
        switch (id) {
            case 1:
                return TAXI;
            case 2:
                return UBER;
            case 3:
                return CABIFY;
            default:
                throw new IllegalArgumentException("Tipo de Profesional Invalido");
        }
    }

}
