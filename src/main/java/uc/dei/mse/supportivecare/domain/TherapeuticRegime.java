package uc.dei.mse.supportivecare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Regime terapêutico.
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
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Acrónimo.
     */
    @Column(name = "acronym")
    private String acronym;

    /**
     * Propósito.
     */
    @NotNull
    @Column(name = "purpose", nullable = false)
    private String purpose;

    /**
     * Condição para administração.
     */
    @NotNull
    @Column(name = "condition", nullable = false)
    private String condition;

    /**
     * Calendarização.
     */
    @Column(name = "timing")
    private String timing;

    /**
     * Indicação para prescrição.
     */
    @NotNull
    @Column(name = "indication", nullable = false)
    private String indication;

    /**
     * Critério de redução de dose.
     */
    @NotNull
    @Column(name = "criteria", nullable = false)
    private String criteria;

    /**
     * Outras informações.
     */
    @Column(name = "notice")
    private String notice;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Treatment treatment;

    @OneToMany(mappedBy = "therapeuticRegime")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Drug> drugs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "therapeuticRegimes", allowSetters = true)
    private Diagnostic diagnostic;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TherapeuticRegime name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public TherapeuticRegime acronym(String acronym) {
        this.acronym = acronym;
        return this;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getPurpose() {
        return purpose;
    }

    public TherapeuticRegime purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCondition() {
        return condition;
    }

    public TherapeuticRegime condition(String condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTiming() {
        return timing;
    }

    public TherapeuticRegime timing(String timing) {
        this.timing = timing;
        return this;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getIndication() {
        return indication;
    }

    public TherapeuticRegime indication(String indication) {
        this.indication = indication;
        return this;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getCriteria() {
        return criteria;
    }

    public TherapeuticRegime criteria(String criteria) {
        this.criteria = criteria;
        return this;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getNotice() {
        return notice;
    }

    public TherapeuticRegime notice(String notice) {
        this.notice = notice;
        return this;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public TherapeuticRegime treatment(Treatment treatment) {
        this.treatment = treatment;
        return this;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public Set<Drug> getDrugs() {
        return drugs;
    }

    public TherapeuticRegime drugs(Set<Drug> drugs) {
        this.drugs = drugs;
        return this;
    }

    public TherapeuticRegime addDrug(Drug drug) {
        this.drugs.add(drug);
        drug.setTherapeuticRegime(this);
        return this;
    }

    public TherapeuticRegime removeDrug(Drug drug) {
        this.drugs.remove(drug);
        drug.setTherapeuticRegime(null);
        return this;
    }

    public void setDrugs(Set<Drug> drugs) {
        this.drugs = drugs;
    }

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    public TherapeuticRegime diagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
        return this;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
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
        return 31;
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
