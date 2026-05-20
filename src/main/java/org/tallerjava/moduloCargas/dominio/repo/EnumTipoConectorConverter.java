package org.tallerjava.moduloCargas.dominio.repo;

import org.tallerjava.moduloCargas.dominio.EnumTipoConector;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EnumTipoConectorConverter implements AttributeConverter<EnumTipoConector, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EnumTipoConector attribute) {
        return attribute.getId();
    }

    @Override
    public EnumTipoConector convertToEntityAttribute(Integer dbData) {
        return EnumTipoConector.getById(dbData);
    }
}
