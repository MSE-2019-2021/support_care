package uc.dei.mse.supportivecare.config;

import java.util.Optional;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Component;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;

/**
 * AttributeConverter<EntityFeedback, String>. Implements the following methods :
 * <ul>
 * <li>convertToDatabaseColumn : (given an Enum returns a String)
 * <li>convertToEntityAttribute : (given a String returns an Enum)
 * </ul>
 */
@Component
@Converter(autoApply = true)
public class EntityFeedbackEnumToAttributeConverter implements AttributeConverter<EntityFeedback, String> {

    @Override
    public String convertToDatabaseColumn(final EntityFeedback entityFeedback) {
        return Optional.ofNullable(entityFeedback).map(EntityFeedback::getValue).orElse(null);
    }

    @Override
    public EntityFeedback convertToEntityAttribute(final String dbEntityFeedback) {
        return EntityFeedback.decode(dbEntityFeedback);
    }
}
