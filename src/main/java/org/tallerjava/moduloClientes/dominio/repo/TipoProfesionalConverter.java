package org.tallerjava.moduloClientes.dominio.repo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.tallerjava.moduloClientes.dominio.EnumTipoProfesional;

//por lo que vi el profe usa esto para manejar enums con JPA
@Converter(autoApply = true)
public class TipoProfesionalConverter implements AttributeConverter<EnumTipoProfesional, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoProfesional attribute){return attribute.getId();}

    @Override
    public EnumTipoProfesional convertToEntityAttribute(Integer dbData){return EnumTipoProfesional.getById(dbData);}

}
