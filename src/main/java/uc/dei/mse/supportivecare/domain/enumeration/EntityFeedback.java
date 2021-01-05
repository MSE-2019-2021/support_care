package uc.dei.mse.supportivecare.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;

/**
 * The EntityFeedback enumeration.
 */
public enum EntityFeedback {
    ACTIVE_SUBSTANCE("active-substance"),
    THERAPEUTIC_REGIME("therapeutic-regime"),
    OUTCOME("outcome"),
    SYMPTOM("symptom");

    private final String value;

    EntityFeedback(String value) {
        this.value = value;
    }

    @JsonCreator
    public static EntityFeedback decode(final String value) {
        return Stream.of(EntityFeedback.values()).filter(targetEnum -> targetEnum.value.equals(value)).findFirst().orElse(null);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
