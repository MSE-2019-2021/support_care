package uc.dei.mse.supportivecare.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configure the converter for the Entity Feedback Enum.
 */
@Configuration
public class EntityFeedbackConfiguration implements WebMvcConfigurer {

    public EntityFeedbackConfiguration() {
        super();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEntityFeedbackEnumConverter());
    }
}
