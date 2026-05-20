package org.tallerjava.moduloCargas.dominio.repo;

import org.tallerjava.moduloCargas.dominio.EnumTipoCargador;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EnumTipoCargadorConverter implements AttributeConverter<EnumTipoCargador, Integer> {
    
    @Override
    public Integer convertToDatabaseColumn(EnumTipoCargador attribute) {
        return attribute.getId();
    }

    @Override
    public EnumTipoCargador convertToEntityAttribute(Integer dbData) {
        return EnumTipoCargador.getById(dbData);
    }
}
