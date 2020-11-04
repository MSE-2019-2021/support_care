package uc.dei.mse.supportivecare.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uc.dei.mse.supportivecare.GeneratedByJHipster;

/**
 * Observação.
 */
@Entity
@Table(name = "notice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@GeneratedByJHipster
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Descrição.
     */
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Avaliação.
     */
    @NotNull
    @Column(name = "evaluation", nullable = false)
    private String evaluation;

    /**
     * Intervenção.
     */
    @NotNull
    @Column(name = "intervention", nullable = false)
    private String intervention;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "notices", "administration", "therapeuticRegimes" }, allowSetters = true)
    private Drug drug;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Notice id(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Notice description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public Notice evaluation(String evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getIntervention() {
        return intervention;
    }

    public Notice intervention(String intervention) {
        this.intervention = intervention;
        return this;
    }

    public void setIntervention(String intervention) {
        this.intervention = intervention;
    }

    public Drug getDrug() {
        return drug;
    }

    public Notice drug(Drug drug) {
        this.drug = drug;
        return this;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notice)) {
            return false;
        }
        return id != null && id.equals(((Notice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notice{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", evaluation='" + getEvaluation() + "'" +
            ", intervention='" + getIntervention() + "'" +
            "}";
    }
}
