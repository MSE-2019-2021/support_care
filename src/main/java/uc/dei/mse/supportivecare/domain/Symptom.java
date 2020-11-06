package uc.dei.mse.supportivecare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Sintoma (Efeito secundário).
 */
@Entity
@Table(name = "symptom")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Symptom extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Informação ao enfermeiro.
     */
    @Column(name = "notice")
    private String notice;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "symptom_therapeutic_regime",
        joinColumns = @JoinColumn(name = "symptom_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "therapeutic_regime_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties(value = { "drugs", "treatment", "symptoms" }, allowSetters = true)
    private Set<TherapeuticRegime> therapeuticRegimes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "symptom_outcome",
        joinColumns = @JoinColumn(name = "symptom_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "outcome_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties(value = { "documents", "symptoms" }, allowSetters = true)
    private Set<Outcome> outcomes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "symptom_toxicity_rate",
        joinColumns = @JoinColumn(name = "symptom_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "toxicity_rate_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties(value = { "symptoms" }, allowSetters = true)
    private Set<ToxicityRate> toxicityRates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Symptom id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Symptom name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotice() {
        return notice;
    }

    public Symptom notice(String notice) {
        this.notice = notice;
        return this;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Set<TherapeuticRegime> getTherapeuticRegimes() {
        return therapeuticRegimes;
    }

    public Symptom therapeuticRegimes(Set<TherapeuticRegime> therapeuticRegimes) {
        this.therapeuticRegimes = therapeuticRegimes;
        return this;
    }

    public Symptom addTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegimes.add(therapeuticRegime);
        therapeuticRegime.getSymptoms().add(this);
        return this;
    }

    public Symptom removeTherapeuticRegime(TherapeuticRegime therapeuticRegime) {
        this.therapeuticRegimes.remove(therapeuticRegime);
        therapeuticRegime.getSymptoms().remove(this);
        return this;
    }

    public void setTherapeuticRegimes(Set<TherapeuticRegime> therapeuticRegimes) {
        this.therapeuticRegimes = therapeuticRegimes;
    }

    public Set<Outcome> getOutcomes() {
        return outcomes;
    }

    public Symptom outcomes(Set<Outcome> outcomes) {
        this.outcomes = outcomes;
        return this;
    }

    public Symptom addOutcome(Outcome outcome) {
        this.outcomes.add(outcome);
        outcome.getSymptoms().add(this);
        return this;
    }

    public Symptom removeOutcome(Outcome outcome) {
        this.outcomes.remove(outcome);
        outcome.getSymptoms().remove(this);
        return this;
    }

    public void setOutcomes(Set<Outcome> outcomes) {
        this.outcomes = outcomes;
    }

    public Set<ToxicityRate> getToxicityRates() {
        return toxicityRates;
    }

    public Symptom toxicityRates(Set<ToxicityRate> toxicityRates) {
        this.toxicityRates = toxicityRates;
        return this;
    }

    public Symptom addToxicityRate(ToxicityRate toxicityRate) {
        this.toxicityRates.add(toxicityRate);
        toxicityRate.getSymptoms().add(this);
        return this;
    }

    public Symptom removeToxicityRate(ToxicityRate toxicityRate) {
        this.toxicityRates.remove(toxicityRate);
        toxicityRate.getSymptoms().remove(this);
        return this;
    }

    public void setToxicityRates(Set<ToxicityRate> toxicityRates) {
        this.toxicityRates = toxicityRates;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Symptom)) {
            return false;
        }
        return id != null && id.equals(((Symptom) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Symptom{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", notice='" + getNotice() + "'" +
            "}";
    }
}