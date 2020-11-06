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
 * Grau de Toxicidade.
 */
@Entity
@Table(name = "toxicity_rate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ToxicityRate extends AbstractAuditingEntity implements Serializable {

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
     * Descrição.
     */
    @Column(name = "description")
    private String description;

    /**
     * Informação ao doente.
     */
    @Column(name = "notice")
    private String notice;

    /**
     * Intervenção autónoma.
     */
    @Column(name = "autonomous_intervention")
    private String autonomousIntervention;

    /**
     * Intervenção interdependente.
     */
    @Column(name = "interdependent_intervention")
    private String interdependentIntervention;

    /**
     * Suporte para auto-gestão.
     */
    @Column(name = "self_management")
    private String selfManagement;

    @ManyToMany(mappedBy = "toxicityRates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "therapeuticRegimes", "outcomes", "toxicityRates" }, allowSetters = true)
    private Set<Symptom> symptoms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ToxicityRate id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ToxicityRate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ToxicityRate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotice() {
        return notice;
    }

    public ToxicityRate notice(String notice) {
        this.notice = notice;
        return this;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getAutonomousIntervention() {
        return autonomousIntervention;
    }

    public ToxicityRate autonomousIntervention(String autonomousIntervention) {
        this.autonomousIntervention = autonomousIntervention;
        return this;
    }

    public void setAutonomousIntervention(String autonomousIntervention) {
        this.autonomousIntervention = autonomousIntervention;
    }

    public String getInterdependentIntervention() {
        return interdependentIntervention;
    }

    public ToxicityRate interdependentIntervention(String interdependentIntervention) {
        this.interdependentIntervention = interdependentIntervention;
        return this;
    }

    public void setInterdependentIntervention(String interdependentIntervention) {
        this.interdependentIntervention = interdependentIntervention;
    }

    public String getSelfManagement() {
        return selfManagement;
    }

    public ToxicityRate selfManagement(String selfManagement) {
        this.selfManagement = selfManagement;
        return this;
    }

    public void setSelfManagement(String selfManagement) {
        this.selfManagement = selfManagement;
    }

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public ToxicityRate symptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
        return this;
    }

    public ToxicityRate addSymptom(Symptom symptom) {
        this.symptoms.add(symptom);
        symptom.getToxicityRates().add(this);
        return this;
    }

    public ToxicityRate removeSymptom(Symptom symptom) {
        this.symptoms.remove(symptom);
        symptom.getToxicityRates().remove(this);
        return this;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ToxicityRate)) {
            return false;
        }
        return id != null && id.equals(((ToxicityRate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ToxicityRate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", notice='" + getNotice() + "'" +
            ", autonomousIntervention='" + getAutonomousIntervention() + "'" +
            ", interdependentIntervention='" + getInterdependentIntervention() + "'" +
            ", selfManagement='" + getSelfManagement() + "'" +
            "}";
    }
}
