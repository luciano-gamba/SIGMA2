package org.tallerjava.moduloCargas.dominio;

public enum EnumTipoConector {
    
    // Tipo 2 , CCS2, CYHAdeMO, GB/T
    Tipo2(1),
    CCS2(2),
    CYHAdeMO(3),
    GB_T(4);

    private int id;
    EnumTipoConector(int id) { this.id = id; }
    public int getId() { return id; }

    public static EnumTipoConector getById(int id) {
        switch (id) {
            case 1:
                return Tipo2;
            case 2:
                return CCS2;
            case 3:
                return CYHAdeMO;
            case 4:
                return GB_T;
            default:
                throw new IllegalArgumentException("Tipo de Conector no reconocido");
        }
    }
}
