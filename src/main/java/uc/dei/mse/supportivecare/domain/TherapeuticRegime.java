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
 * Regime Terapêutico.
 */
@Entity
@Table(name = "therapeutic_regime")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TherapeuticRegime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    /**
     * Acrônimo.
     */
    @Size(max = 50)
    @Column(name = "acronym", length = 50)
    private String acronym;

    /**
     * Propósito.
     */
    @NotNull
    @Size(max = 1000)
    @Column(name = "purpose", length = 1000, nullable = false)
    private String purpose;

    /**
     * Condições para administração.
     */
    @NotNull
    @Size(max = 1000)
    @Column(name = "condition", length = 1000, nullable = false)
    private String condition;

    /**
     * Calendarização.
     */
    @Size(max = 255)
    @Column(name = "timing", length = 255)
    private String timing;

    /**
     * Indicação para prescrição.
     */
    @NotNull
    @Size(max = 1000)
    @Column(name = "indication", length = 1000, nullable = false)
    private String indication;

    /**
     * Critérios de redução de dose.
     */
    @NotNull
    @Size(max = 1000)
    @Column(name = "criteria", length = 1000, nullable = false)
    private String criteria;

    /**
     * Outras informações.
     */
    @Size(max = 1000)
    @Column(name = "notice", length = 1000)
    private String notice;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_therapeutic_regime__active_substance",
        joinColumns = @JoinColumn(name = "therapeutic_regime_id"),
        inverseJoinColumns = @JoinColumn(name = "active_substance_id")
    )
    @JsonIgnoreProperties(value = { "notices", "administration", "therapeuticRegimes" }, allowSetters = true)
    private Set<ActiveSubstance> activeSubstances = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "therapeuticRegimes" }, allowSetters = true)
    private Treatment treatment;

    @ManyToMany(mappedBy = "therapeuticRegimes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "toxicityRates", "therapeuticRegimes", "outcomes" }, allowSetters = true)
    private Set<Symptom> symptoms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TherapeuticRegime id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public TherapeuticRegime name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public TherapeuticRegime acronym(String acronym) {
        this.acronym = acronym;
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public TherapeuticRegime purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCondition() {
        return this.condition;
    }

    public TherapeuticRegime condition(String condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTiming() {
        return this.timing;
    }

    public TherapeuticRegime timing(String timing) {
        this.timing = timing;
        return this;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getIndication() {
        return this.indication;
    }

    public TherapeuticRegime indication(String indication) {
        this.indication = indication;
        return this;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getCriteria() {
        return this.criteria;
    }

    public TherapeuticRegime criteria(String criteria) {
        this.criteria = criteria;
        return this;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getNotice() {
        return this.notice;
    }

    public TherapeuticRegime notice(String notice) {
        this.notice = notice;
        return this;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Set<ActiveSubstance> getActiveSubstances() {
        return this.activeSubstances;
    }

    public TherapeuticRegime activeSubstances(Set<ActiveSubstance> activeSubstances) {
        this.setActiveSubstances(activeSubstances);
        return this;
    }

    public TherapeuticRegime addActiveSubstance(ActiveSubstance activeSubstance) {
        this.activeSubstances.add(activeSubstance);
        activeSubstance.getTherapeuticRegimes().add(this);
        return this;
    }

    public TherapeuticRegime removeActiveSubstance(ActiveSubstance activeSubstance) {
        this.activeSubstances.remove(activeSubstance);
        activeSubstance.getTherapeuticRegimes().remove(this);
        return this;
    }

    public void setActiveSubstances(Set<ActiveSubstance> activeSubstances) {
        this.activeSubstances = activeSubstances;
    }

    public Treatment getTreatment() {
        return this.treatment;
    }

    public TherapeuticRegime treatment(Treatment treatment) {
        this.setTreatment(treatment);
        return this;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public Set<Symptom> getSymptoms() {
        return this.symptoms;
    }

    public TherapeuticRegime symptoms(Set<Symptom> symptoms) {
        this.setSymptoms(symptoms);
        return this;
    }

    public TherapeuticRegime addSymptom(Symptom symptom) {
        this.symptoms.add(symptom);
        symptom.getTherapeuticRegimes().add(this);
        return this;
    }

    public TherapeuticRegime removeSymptom(Symptom symptom) {
        this.symptoms.remove(symptom);
        symptom.getTherapeuticRegimes().remove(this);
        return this;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        if (this.symptoms != null) {
            this.symptoms.forEach(i -> i.removeTherapeuticRegime(this));
        }
        if (symptoms != null) {
            symptoms.forEach(i -> i.addTherapeuticRegime(this));
        }
        this.symptoms = symptoms;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TherapeuticRegime)) {
            return false;
        }
        return id != null && id.equals(((TherapeuticRegime) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TherapeuticRegime{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", acronym='" + getAcronym() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", condition='" + getCondition() + "'" +
            ", timing='" + getTiming() + "'" +
            ", indication='" + getIndication() + "'" +
            ", criteria='" + getCriteria() + "'" +
            ", notice='" + getNotice() + "'" +
            "}";
    }
}
