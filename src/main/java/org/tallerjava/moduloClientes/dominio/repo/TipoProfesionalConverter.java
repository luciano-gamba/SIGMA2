package org.tallerjava.moduloClientes.dominio.repo;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.tallerjava.moduloClientes.dominio.EnumTipoProfesional;

@Converter(autoApply = true)
public class TipoProfesionalConverter implements AttributeConverter<EnumTipoProfesional, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoProfesional attribute){return attribute.getId();}

    @Override
    public EnumTipoProfesional convertToEntityAttribute(Integer dbData){return EnumTipoProfesional.getById(dbData);}

}
