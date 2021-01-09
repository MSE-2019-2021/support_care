package uc.dei.mse.supportivecare.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import uc.dei.mse.supportivecare.domain.enumeration.EntityFeedback;

/**
 * Convert String to Entity Feedback Enum.
 */
@Component
public class StringToEntityFeedbackEnumConverter implements Converter<String, EntityFeedback> {

    @Override
    public EntityFeedback convert(String source) {
        return EntityFeedback.decode(source);
    }
}
