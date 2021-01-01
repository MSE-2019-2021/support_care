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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_symptom_id_seq")
    @SequenceGenerator(name = "gen_symptom_id_seq", sequenceName = "symptom_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    /**
     * Informação ao enfermeiro.
     */
    @Size(max = 1000)
    @Column(name = "notice", length = 1000)
    private String notice;

    @OneToMany(mappedBy = "symptom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "symptom" }, allowSetters = true)
    private Set<ToxicityRate> toxicityRates = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_symptom__therapeutic_regime",
        joinColumns = @JoinColumn(name = "symptom_id"),
        inverseJoinColumns = @JoinColumn(name = "therapeutic_regime_id")
    )
    @JsonIgnoreProperties(value = { "activeSubstances", "treatment", "symptoms" }, allowSetters = true)
    private Set<TherapeuticRegime> therapeuticRegimes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_symptom__outcome",
        joinColumns = @JoinColumn(name = "symptom_id"),
        inverseJoinColumns = @JoinColumn(name = "outcome_id")
    )
    @JsonIgnoreProperties(value = { "documents", "symptoms" }, allowSetters = true)
    private Set<Outcome> outcomes = new HashSet<>();

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
        return this.name;
    }

    public Symptom name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotice() {
        return this.notice;
    }

    public Symptom notice(String notice) {
        this.notice = notice;
        return this;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Set<ToxicityRate> getToxicityRates() {
        return this.toxicityRates;
    }

    public Symptom toxicityRates(Set<ToxicityRate> toxicityRates) {
        this.setToxicityRates(toxicityRates);
        return this;
    }

    public Symptom addToxicityRate(ToxicityRate toxicityRate) {
        this.toxicityRates.add(toxicityRate);
        toxicityRate.setSymptom(this);
        return this;
    }

    public Symptom removeToxicityRate(ToxicityRate toxicityRate) {
        this.toxicityRates.remove(toxicityRate);
        toxicityRate.setSymptom(null);
        return this;
    }

    public void setToxicityRates(Set<ToxicityRate> toxicityRates) {
        if (this.toxicityRates != null) {
            this.toxicityRates.forEach(i -> i.setSymptom(null));
        }
        if (toxicityRates != null) {
            toxicityRates.forEach(i -> i.setSymptom(this));
        }
        this.toxicityRates = toxicityRates;
    }

    public Set<TherapeuticRegime> getTherapeuticRegimes() {
        return this.therapeuticRegimes;
    }

    public Symptom therapeuticRegimes(Set<TherapeuticRegime> therapeuticRegimes) {
        this.setTherapeuticRegimes(therapeuticRegimes);
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
        return this.outcomes;
    }

    public Symptom outcomes(Set<Outcome> outcomes) {
        this.setOutcomes(outcomes);
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
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
