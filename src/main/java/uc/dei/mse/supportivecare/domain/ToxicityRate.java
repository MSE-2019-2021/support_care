package uc.dei.mse.supportivecare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_toxicity_rate_id_seq")
    @SequenceGenerator(name = "gen_toxicity_rate_id_seq", sequenceName = "toxicity_rate_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    /**
     * Nome.
     */
    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    /**
     * Descrição.
     */
    @Size(max = 3000)
    @Column(name = "description", length = 3000)
    private String description;

    /**
     * Informação ao doente.
     */
    @Size(max = 3000)
    @Column(name = "notice", length = 3000)
    private String notice;

    /**
     * Intervenção autónoma.
     */
    @Size(max = 3000)
    @Column(name = "autonomous_intervention", length = 3000)
    private String autonomousIntervention;

    /**
     * Intervenção interdependente.
     */
    @Size(max = 3000)
    @Column(name = "interdependent_intervention", length = 3000)
    private String interdependentIntervention;

    /**
     * Suporte para auto-gestão.
     */
    @Size(max = 3000)
    @Column(name = "self_management", length = 3000)
    private String selfManagement;

    @ManyToOne
    @JsonIgnoreProperties(value = { "toxicityRates", "therapeuticRegimes", "outcomes" }, allowSetters = true)
    private Symptom symptom;

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
        return this.name;
    }

    public ToxicityRate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public ToxicityRate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotice() {
        return this.notice;
    }

    public ToxicityRate notice(String notice) {
        this.notice = notice;
        return this;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getAutonomousIntervention() {
        return this.autonomousIntervention;
    }

    public ToxicityRate autonomousIntervention(String autonomousIntervention) {
        this.autonomousIntervention = autonomousIntervention;
        return this;
    }

    public void setAutonomousIntervention(String autonomousIntervention) {
        this.autonomousIntervention = autonomousIntervention;
    }

    public String getInterdependentIntervention() {
        return this.interdependentIntervention;
    }

    public ToxicityRate interdependentIntervention(String interdependentIntervention) {
        this.interdependentIntervention = interdependentIntervention;
        return this;
    }

    public void setInterdependentIntervention(String interdependentIntervention) {
        this.interdependentIntervention = interdependentIntervention;
    }

    public String getSelfManagement() {
        return this.selfManagement;
    }

    public ToxicityRate selfManagement(String selfManagement) {
        this.selfManagement = selfManagement;
        return this;
    }

    public void setSelfManagement(String selfManagement) {
        this.selfManagement = selfManagement;
    }

    public Symptom getSymptom() {
        return this.symptom;
    }

    public ToxicityRate symptom(Symptom symptom) {
        this.setSymptom(symptom);
        return this;
    }

    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
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
