package uc.dei.mse.supportivecare.domain.enumeration;

/**
 * The EntityFeedback enumeration.
 */
public enum EntityFeedback {
    ACTIVE_SUBSTANCE("ActiveSubstance"),
    THERAPEUTIC_REGIME("TherapeuticRegime"),
    OUTCOME("Outcome"),
    SYMPTOM("Symptom");

    private final String value;

    EntityFeedback(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
